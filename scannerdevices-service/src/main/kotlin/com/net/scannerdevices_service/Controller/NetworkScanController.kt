package com.net.scannerdevices_service.Controller

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

    @GetMapping("/scan-all")
    fun getLastScan(): ResponseEntity<ScanResult> {
        return ResponseEntity.ok(networkScanService.getLastScanResult())
    }

    @GetMapping("/routers/{id}")
    fun getRouterById(@PathVariable id: Long): ResponseEntity<RouterResult> {
        val router = routerRepository.findById(id).orElseThrow { RuntimeException("Roteador não foi encontrado") }
        return ResponseEntity.ok(RouterResult(router.id, router.ssid, router.protocol, router.securityType))
    }
}
