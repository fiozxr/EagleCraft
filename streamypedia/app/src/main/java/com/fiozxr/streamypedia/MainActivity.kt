package com.fiozxr.streamypedia

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fiozxr.streamypedia.data.AppDatabase
import com.fiozxr.streamypedia.data.MediaScanner
import com.fiozxr.streamypedia.ui.StreamypediaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ServerService.startService(this)

        setContent {
            StreamypediaTheme {
                val navController = rememberNavController()
                var permissionsGranted by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        arrayOf(Manifest.permission.READ_MEDIA_VIDEO)
                    } else {
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                    requestPermissions(permissions, 1)
                    permissionsGranted = true

                    val database = AppDatabase.getDatabase(this@MainActivity)
                    MediaScanner(this@MainActivity, database.mediaDao()).scan()
                }

                if (permissionsGranted) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val database = AppDatabase.getDatabase(this@MainActivity)
                            HomeScreen(movies = database.mediaDao().getAllMovies()) { movieId ->
                                navController.navigate("player/$movieId")
                            }
                        }
                        composable("player/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            if (movieId != null) {
                                PlayerScreen(movieId)
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Requesting permissions...")
                    }
                }
            }
        }
    }
}
