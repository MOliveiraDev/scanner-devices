package com.net.scannerdevices_service.DataBase.Entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "routers_tb")
class Router (

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "SSID_name", nullable = false, unique = true)
    val ssid: String,

    @Column(name = "protocol", nullable = false)
    val protocol: String,

    @Column(name = "security_type", nullable = false)
    val securityType: String,

    @OneToMany(mappedBy = "router", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    val devices: List<Device> = emptyList()

)