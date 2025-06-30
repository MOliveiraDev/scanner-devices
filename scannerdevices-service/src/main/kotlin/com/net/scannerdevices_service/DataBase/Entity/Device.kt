package com.net.scannerdevices_service.DataBase.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.FetchType
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime

@Entity
@Table(name = "devices_tb")
class Device (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "ip_address")
    val ip: String,

    @Column(name = "device_name", nullable = true, unique = true)
    val device: String?,

    @Column(name = "connected")
    val connected: Boolean,

    @Column(name = "last_connection")
    val lastConnection: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "router_id")
    val router: Router


)