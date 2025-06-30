package com.net.scannerdevices_service.DataBase.Repository

import com.net.scannerdevices_service.DataBase.Entity.Router
import org.springframework.data.jpa.repository.JpaRepository

interface RouterRepository : JpaRepository<Router, Long> {
}