package com.net.scannerdevices_service.Controller

import com.net.scannerdevices_service.DTO.DeviceResult
import com.net.scannerdevices_service.DTO.RouterResult
import com.net.scannerdevices_service.DTO.ScanResult
import com.net.scannerdevices_service.DataBase.Repository.RouterRepository
import com.net.scannerdevices_service.Service.NetworkScanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/network")
class NetworkScanController(
    private val networkScanService: NetworkScanService,
    private val routerRepository: RouterRepository
) {

    @PostMapping("/scan-devices")
    fun triggerScan(): ResponseEntity<Void> {
        networkScanService.scanAndSave()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/routers/{id}")
    fun getRouterById(@PathVariable id: Long): ResponseEntity<RouterResult> {
        val router = routerRepository.findById(id).orElseThrow { RuntimeException("Roteador não foi encontrado") }
        return ResponseEntity.ok(RouterResult(router.id, router.ssid, router.protocol, router.securityType))
    }

    @GetMapping("/Devices/{routerId}")
    fun getDevicesByRouterId(@PathVariable routerId: Long): ResponseEntity<List<DeviceResult>> {
        val devices = routerRepository.findById(routerId)
            .orElseThrow { RuntimeException("Roteador não foi encontrado") }
            .devices.map { device ->
                DeviceResult(
                    ip = device.ip,
                    routerId = device.router.id,
                    device = device.device,
                    connected = device.connected,
                    lastConnection = device.lastConnection
                )
            }
        return ResponseEntity.ok(devices)
    }

    @GetMapping("/AllRouters")
    fun getAllRouters(): ResponseEntity<List<RouterResult>> {
        val routers = routerRepository.findAll().map { RouterResult(it.id, it.ssid, it.protocol, it.securityType) }
        return ResponseEntity.ok(routers)
    }

    @GetMapping("/all-devices-for-routers")
    fun getAllDevices(): ResponseEntity<List<ScanResult>> {
        val routers = routerRepository.findAll()
        val scanResults = routers.map { router ->
            ScanResult(
                RouterResult(router.id, router.ssid, router.protocol, router.securityType),
                router.devices.map { device ->
                    DeviceResult(
                        ip = device.ip,
                        routerId = device.router.id,
                        device = device.device,
                        connected = device.connected,
                        lastConnection = device.lastConnection
                    )
                }
            )
        }
        return ResponseEntity.ok(scanResults)
    }
}