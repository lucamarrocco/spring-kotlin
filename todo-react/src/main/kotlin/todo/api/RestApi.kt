package todo.api

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json

open class RestApi(val baseUrl: String) {

    suspend inline fun <reified EntityType> post(path: String, body: EntityType? = null): EntityType =
        request("POST", path, body)

    suspend inline fun <reified EntityType> get(path: String, body: EntityType? = null): List<EntityType> =
        request("GET", path, body)

    suspend inline fun <reified T, reified R> request(method: String, path: String, entity: T?): R {
        val response = window
            .fetch(
                "$baseUrl/$path",
                RequestInit(
                    mode = RequestMode.CORS,
                    method = method,
                    headers = json().apply { this["content-type"] = "application/json" },
                    body = entity?.let { Json.encodeToString(entity) },
                )
            )
            .await()
            .text()
            .await()
        return Json.decodeFromString(response)
    }
}
