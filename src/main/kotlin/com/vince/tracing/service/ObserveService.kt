package com.vince.tracing.service

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.annotation.Observed
import io.micrometer.tracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ObserveService {
    private val random: Random = Random()

    companion object {
        private val log = LoggerFactory.getLogger(ObserveService::class.java)
    }

    @Autowired
    lateinit var registry: ObservationRegistry

    @Autowired
    lateinit var tracer: Tracer

    @Observed(name = "annotation.method", contextualName = "annotation", lowCardinalityKeyValues = ["annotation", "annotation1"])
    fun annotation(id: String?): String? {
        try {
            Thread.sleep(random.nextLong(200L)) // simulates latency
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        return "annotation"
    }

    //This demo the usage of observation
    fun programmatic() {
        val observation = Observation.createNotStarted("programmatic", registry).start()
        try {
            observation.openScope().use { scope ->
                try {
                    Thread.sleep(random.nextLong(200L)) // simulates latency
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
            }
        } catch (exception: Exception) {
            observation.error(exception)
            throw exception
        } finally {
            observation.stop()
        }
    }

    fun getActiveSpan() {
        log.info("Tracer ID " + tracer.currentSpan()!!.context()!!.spanId())
    }
}