package com.net.scannerdevices_service.DTO

data class ScanResult(
    val router: RouterResult,
    val devices: List<DeviceResult>
)
