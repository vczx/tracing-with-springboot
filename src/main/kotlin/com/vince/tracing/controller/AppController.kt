package com.vince.tracing.controller

import com.vince.tracing.service.ObserveService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


@RestController
class AppController {

    private val logger = LoggerFactory.getLogger(AppController::class.java)

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var observeService: ObserveService

    /**
     * This demo the default filter by springboot which creates the Span
     */
    @GetMapping("/annotation")
    fun annotation(): ResponseEntity<*> {
        logger.info("called by annotation")
        return ResponseEntity.ok("response from /annotation called annotation")
    }

    @GetMapping("/programmatic")
    fun programmatic(): ResponseEntity<*> {
        logger.info("called by programmatic")
        return ResponseEntity.ok("response from /programmatic called programmatic")
    }

    @GetMapping("/activeSpan")
    fun activeSpan(): ResponseEntity<*> {
        logger.info("called by activeSpan")
        observeService.getActiveSpan()
        return ResponseEntity.ok("response from /activeSpan called activeSpan")
    }

    @GetMapping("/propagate")
    fun crossApplication(): ResponseEntity<*>? {
        logger.info("called service2")
        val response: String? = restTemplate.getForObject("http://localhost:8082/annotation", String::class.java)
        observeService.annotation("dummy")
        return ResponseEntity.ok("response : $response")
    }
}