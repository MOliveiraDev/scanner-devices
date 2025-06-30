package com.net.scannerdevices_service.DataBase.Repository

import com.net.scannerdevices_service.DataBase.Entity.Router
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RouterRepository : JpaRepository<Router, Long> {
    fun findBySsidAndProtocolAndSecurityType(ssid: String, protocol: String, securityType: String): Router?
    override fun findById(id: Long): Optional<Router>
}