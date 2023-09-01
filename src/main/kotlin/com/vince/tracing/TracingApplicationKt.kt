package com.vince.tracing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TracingApplicationKt

fun main(args: Array<String>) {
	runApplication<TracingApplicationKt>(*args)
}
