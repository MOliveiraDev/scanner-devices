package com.net.scannerdevices_service.DTO

data class RouterResult(
    val id: Long,
    val ssid: String,
    val protocol: String,
    val securityType: String
)
