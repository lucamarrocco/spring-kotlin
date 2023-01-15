package todo.api

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.js.Object
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json


open class RestApi(val baseUrl: String) {

    suspend inline fun <reified InputType, reified OutputType> get(
        path: String, query: InputType? = null
    ): List<OutputType> =
        Json.decodeFromDynamic(
            window.fetch(
                "$baseUrl/$path?${toQueryString(query)}", RequestInit(
                    mode = RequestMode.CORS,
                    method = "GET",
                    headers = json().apply { this["content-type"] = "application/json" },
                )
            ).await().json().await()
        )

    suspend inline fun <reified OutputType> post(
        path: String, body: OutputType? = null
    ): OutputType =
        Json.decodeFromDynamic(
            window.fetch(
                "$baseUrl/$path", RequestInit(
                    mode = RequestMode.CORS,
                    method = "POST",
                    headers = json().apply { this["content-type"] = "application/json" },
                    body = body?.let { Json.encodeToString(body) },
                )
            ).await().json().await()
        )

    companion object {
        inline fun <reified Q> toQueryString(query: Q) =
            Object.entries(JSON.parse<Any>(Json.encodeToString(query)))
                .joinToString("&") { (key, value) -> "${key}=${value}" }
    }
}
