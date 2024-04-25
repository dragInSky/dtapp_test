package com.example.dtapp.net

import com.example.dtapp.BuildConfig
import com.example.dtapp.net.transport.TransportHabitInfo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class Client : AutoCloseable {
    private val client = HttpClient(CIO)
    private val key = BuildConfig.DTAPP_KEY

    suspend fun getHabits(): HttpResponse {
        return client.get("https://droid-test-server.doubletapp.ru/api/habit") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, key)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addOrUpdateHabit(habitInfo: TransportHabitInfo): HttpResponse {
        val jsonBody: String = Json.encodeToString(habitInfo)

        return client.put("https://droid-test-server.doubletapp.ru/api/habit") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, key)
                append(HttpHeaders.ContentType, "application/json")
            }
            body = jsonBody
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun deleteHabit(uid: String): HttpResponse {
        val jsonBody = buildJsonObject { put("uid", uid) }.toString()

        return client.delete("https://droid-test-server.doubletapp.ru/api/habit") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, key)
                append(HttpHeaders.ContentType, "application/json")
            }
            body = jsonBody
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun habitDone(date: Int, uid: String): HttpResponse {
        val jsonBody = buildJsonObject {
            put("date", date)
            put("habit_uid", uid)
        }.toString()

        return client.post("https://droid-test-server.doubletapp.ru/api/habit_done") {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, key)
                append(HttpHeaders.ContentType, "application/json")
            }
            body = jsonBody
        }
    }

    override fun close() {
        client.close()
    }
}
