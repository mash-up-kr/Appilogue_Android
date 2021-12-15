package com.anonymous.appilogue.usecase

import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.dto.ImageDto
import com.anonymous.appilogue.repository.ReviewRepository
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UploadAppIconImage @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    @WorkerThread
    suspend operator fun invoke(cacheDir: File, appIconBitmap: Bitmap): UiState<ImageDto> {
        return try {
            val maxKB = (appIconBitmap.byteCount / KB_UNIT).coerceAtLeast(DEFAULT_MAX_FILE_SIZE)
            val format = DEFAULT_IMAGE_EXT
            val file = File(cacheDir, DEFAULT_FILE_NAME)
            file.createNewFile()

            val bos = ByteArrayOutputStream()
            appIconBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapData = bos.toByteArray()

            FileOutputStream(file).apply {
                write(bitmapData)
                flush()
                close()
            }
            reviewRepository.uploadAppIcon(maxKB, format, file)
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    companion object {
        private const val DEFAULT_IMAGE_EXT = "png"
        private const val DEFAULT_FILE_NAME = "app_icon"
        private const val DEFAULT_MAX_FILE_SIZE = 300
        private const val KB_UNIT = 1024
    }
}