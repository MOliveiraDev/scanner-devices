package com.net.scannerdevices_service.DataBase.Repository

import com.net.scannerdevices_service.DataBase.Entity.Device
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device, Long> {
}