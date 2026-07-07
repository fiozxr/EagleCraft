package com.fiozxr.streamypedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.fiozxr.streamypedia.ui.StreamypediaTheme
import com.fiozxr.streamypedia.ui.GlassCard
import com.fiozxr.streamypedia.server.StreamServer

class MainActivity : ComponentActivity() {
    private val server = StreamServer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        server.start()

        setContent {
            StreamypediaTheme {
                GlassCard {
                    Greeting("Streamypedia")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Welcome to $name!")
}
