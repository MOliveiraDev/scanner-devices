package com.net.scannerdevices_service.DTO

import java.time.LocalDateTime

data class DeviceResult(
    val ip: String,
    val device: String?,
    val connected: Boolean,
    val lastConnection: LocalDateTime?
)
