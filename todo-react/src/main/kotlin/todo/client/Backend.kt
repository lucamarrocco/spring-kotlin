package todo.client

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.js.Object
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemsFilter
import kotlin.js.json

abstract class AbstractBackend(val baseUrl: String) {

    protected suspend inline fun <reified OutputType> create(path: String, body: OutputType? = null): OutputType =
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

    protected suspend inline fun <reified InputType, reified OutputType> list(
        path: String,
        query: InputType? = null
    ): Array<OutputType> =
        Json.decodeFromDynamic(
            window.fetch(
                "$baseUrl/$path", RequestInit(
                    mode = RequestMode.CORS,
                    method = "POST",
                    headers = json().apply { this["content-type"] = "application/json" },
                    body = Json.encodeToString(query)
                )
            ).await().json().await()
        )

    companion object {
        protected inline fun <reified Q> toQueryString(query: Q) =
            Object.entries(JSON.parse<Any>(Json.encodeToString(query)))
                .joinToString("&") { (key, value) -> "${key}=${value}" }
    }
}

class Backend(baseUrl: String) : AbstractBackend(baseUrl) {

    suspend fun saveWorkItem(item: WorkItem): WorkItem = create("work-item", item)

    suspend fun listWorkItems(filter: WorkItemsFilter): Array<WorkItem> = list("work-items", filter)
}

val mainScope = MainScope()

val backend = Backend("http://localhost:8080")
