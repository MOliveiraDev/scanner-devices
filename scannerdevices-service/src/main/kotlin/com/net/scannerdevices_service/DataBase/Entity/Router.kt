package com.net.scannerdevices_service.DataBase.Entity

import jakarta.persistence.CascadeType
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

    val ssid: String,
    val protocol: String,
    val securityType: String,

    @OneToMany(mappedBy = "router", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    val devices: List<Device> = emptyList()

)