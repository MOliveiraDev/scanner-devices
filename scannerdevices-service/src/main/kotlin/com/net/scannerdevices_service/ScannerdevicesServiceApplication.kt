package com.net.scannerdevices_service

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScannerdevicesServiceApplication

fun main(args: Array<String>) {
	Dotenv.configure().ignoreIfMissing().load().entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}
	runApplication<ScannerdevicesServiceApplication>(*args)
}
