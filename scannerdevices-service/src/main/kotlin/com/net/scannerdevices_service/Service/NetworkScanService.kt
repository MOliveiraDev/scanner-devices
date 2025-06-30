package com.net.scannerdevices_service.Service

import com.net.scannerdevices_service.DTO.DeviceResult
import com.net.scannerdevices_service.DTO.RouterResult
import com.net.scannerdevices_service.DTO.ScanResult
import com.net.scannerdevices_service.DataBase.Entity.Device
import com.net.scannerdevices_service.DataBase.Entity.Router
import com.net.scannerdevices_service.DataBase.Repository.DeviceRepository
import com.net.scannerdevices_service.DataBase.Repository.RouterRepository
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.time.LocalDateTime

@Service
class NetworkScanService(
    private val routerRepository: RouterRepository,
    private val deviceRepository: DeviceRepository
) {

    fun scanAndSave() {
        val baseIps = getBaseIps()
        val ssid = getSSID()

        val router = routerRepository.findBySsidAndProtocolAndSecurityType(
            ssid, "802.11ac", "WPA2"
        ) ?: routerRepository.save(
            Router(
                ssid = ssid,
                protocol = "802.11ac",
                securityType = "WPA2"
            )
        )

        val foundIps = mutableSetOf<String>()
        val foundHostnames = mutableSetOf<String>()

        runBlocking {
            baseIps.forEach { baseIp ->
                val jobs = (1..254).map { i ->
                    launch(Dispatchers.IO) {
                        val ip = "$baseIp.$i"
                        try {
                            val address = InetAddress.getByName(ip)
                            if (address.isReachable(500)) {
                                val hostname = address.hostName
                                synchronized(foundIps) {
                                    if (foundIps.contains(ip) || foundHostnames.contains(hostname)) return@launch
                                    foundIps.add(ip)
                                    foundHostnames.add(hostname)
                                }
                                val device = Device(
                                    ip = ip,
                                    device = hostname,
                                    connected = true,
                                    lastConnection = LocalDateTime.now(),
                                    router = router
                                )
                                deviceRepository.save(device)
                            }
                        } catch (_: Exception) {}
                    }
                }
                jobs.forEach { it.join() }
            }
        }
    }
    fun getLastScanResult(): ScanResult {
        val router = routerRepository.findAll().maxByOrNull { it.id }
            ?: throw RuntimeException("Nenhum scan encontrado.")

        val devices = deviceRepository.findAll()
            .filter { it.router.id == router.id && it.connected }
            .map {
                DeviceResult(
                    ip = it.ip,
                    device = it.device,
                    connected = it.connected,
                    lastConnection = it.lastConnection
                )
            }

        return ScanResult(
            router = RouterResult(
                id = router.id,
                ssid = router.ssid,
                protocol = router.protocol,
                securityType = router.securityType
            ),
            devices = devices
        )
    }

    private fun getBaseIps(): List<String> {
        val baseIps = mutableSetOf<String>()
        try {
            val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
            for (iface in interfaces) {
                val addresses = iface.inetAddresses
                for (addr in addresses) {
                    val ip = addr.hostAddress
                    if (!ip.contains(":")) {
                        when {
                            ip.startsWith("192.168.") -> baseIps.add(ip.substringBeforeLast("."))
                            ip.startsWith("10.") -> baseIps.add(ip.substringBeforeLast("."))
                            ip.matches(Regex("""172\.(1[6-9]|2[0-9]|3[0-1])\..*""")) -> baseIps.add(ip.substringBeforeLast("."))
                        }
                    }
                }
            }
        } catch (_: Exception) {}
        return baseIps.toList()
    }


    private fun getSSID(): String {
        return try {
            val process = ProcessBuilder("netsh", "wlan", "show", "interfaces")
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val ssidLine = output.lines()
                .firstOrNull { it.trim().startsWith("SSID") && !it.contains("BSSID") }

            ssidLine?.split(":")?.getOrNull(1)?.trim() ?: "Desconhecido"
        } catch (e: Exception) {
            "Desconhecido"
        }
    }
}
