package com.fiozxr.streamypedia.server

import android.content.Context
import android.util.Log
import com.fiozxr.streamypedia.data.AppDatabase
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.firstOrNull
import java.io.File
import io.ktor.server.engine.ApplicationEngine

class StreamServer(private val context: Context) {
    private var server: ApplicationEngine? = null

    fun start() {
        if (server != null) return

        Log.d("StreamServer", "Server starting on port 8096...")

        server = embeddedServer(Netty, port = 8096) {
            install(CORS) {
                anyHost()
            }
            install(ContentNegotiation) {
                // Simplified JSON serialization string building for MVP
            }
            routing {
                get("/") {
                    call.respondText("Streamypedia API", ContentType.Text.Plain)
                }

                get("/api/library") {
                    val dao = AppDatabase.getDatabase(context).mediaDao()
                    val movies = dao.getAllMovies().firstOrNull() ?: emptyList()

                    val json = buildString {
                        append("[")
                        movies.forEachIndexed { index, movie ->
                            append("""{"id":${movie.id},"title":"${movie.title.replace("\"","\\\"")}","filePath":"${movie.filePath.replace("\\","\\\\").replace("\"","\\\"")}"}""")
                            if (index < movies.size - 1) append(",")
                        }
                        append("]")
                    }

                    call.respondText(json, ContentType.Application.Json)
                }

                get("/stream/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id != null) {
                        val dao = AppDatabase.getDatabase(context).mediaDao()
                        val movie = dao.getMovieById(id)

                        if (movie != null) {
                            val file = File(movie.filePath)
                            if (file.exists()) {
                                call.respondFile(file)
                            } else {
                                call.respond(HttpStatusCode.NotFound, "File not found on disk")
                            }
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Movie not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    }
                }
            }
        }.start(wait = false)
    }

    fun stop() {
        Log.d("StreamServer", "Server stopping...")
        server?.stop(1000, 2000)
        server = null
    }
}
