package com.net.scannerdevices_service.DTO

data class ScanRequest(
    val baseIp: String,
    val ssid: String,
    val protocol: String,
    val securityType: String
)
