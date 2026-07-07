package com.fiozxr.streamypedia.server

import android.util.Log

class StreamServer {
    fun start() {
        Log.d("StreamServer", "Server starting on port 8096...")
        // Ktor setup would go here
    }

    fun stop() {
        Log.d("StreamServer", "Server stopping...")
    }
}
