package com.fiozxr.streamypedia.data

import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaScanner(private val context: Context, private val mediaDao: MediaDao) {
    suspend fun scan() {
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA
            )

            val cursor = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            val movies = mutableListOf<Movie>()

            cursor?.use {
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val pathColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

                while (it.moveToNext()) {
                    val title = it.getString(titleColumn)
                    val path = it.getString(pathColumn)

                    movies.add(Movie(title = title, filePath = path))
                }
            }

            if (movies.isNotEmpty()) {
                mediaDao.clearMovies()
                mediaDao.insertMovies(movies)
            }
        }
    }
}
