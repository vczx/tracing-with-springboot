package com.vince.tracing.customhttp

import io.micrometer.observation.transport.Propagator
import io.micrometer.observation.transport.RequestReplySenderContext
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.springframework.lang.Nullable

class HttpClientRequestContext(request: HttpRequest?) :
    RequestReplySenderContext<HttpRequest?, HttpResponse?>(
        Propagator.Setter { request: HttpRequest?, name: String, value: String ->
            setRequestHeader(
                request,
                name,
                value
            )
        }) {
    init {
        setCarrier(request!!)
    }

    companion object {
        private fun setRequestHeader(@Nullable request: HttpRequest?, name: String, value: String) {
            request?.setHeader(name, value)
        }
    }
}

