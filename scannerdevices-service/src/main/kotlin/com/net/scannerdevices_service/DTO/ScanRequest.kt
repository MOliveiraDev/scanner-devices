package com.net.scannerdevices_service.DTO

data class ScanRequest(
    val baseIp: String,           // Ex: "192.168.0"
    val ssid: String,
    val protocol: String,
    val securityType: String
)
