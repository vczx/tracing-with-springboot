package com.vince.tracing.configurations

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class AppConfig() {
    @Bean
    fun otlpHttpSpanExporter(@Value("\${tracing.url}") url: String): OtlpHttpSpanExporter {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build()
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build();
    }

    @Bean
    fun observedAspect(observationRegistry: ObservationRegistry): ObservedAspect {
        return ObservedAspect(observationRegistry)
    }
}