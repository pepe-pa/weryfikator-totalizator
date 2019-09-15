package com.hackyeah.lotto.registration.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hackyeah.lotto.common.viewmodel.RxViewModel
import com.hackyeah.lotto.registration.repository.FaceRecognitionRepository
import com.hackyeah.lotto.registration.repository.TextRecognitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val faceRecognitionRepository: FaceRecognitionRepository,
    private val textRecognitionRepository: TextRecognitionRepository
) : RxViewModel() {

    private var face1: String = ""
    private var face2: String = ""

    fun detectFace(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = faceRecognitionRepository.detectFace(uri)

            Log.i("Piotrek", "result of face recognition  $result")
            face1 = result.firstOrNull()?.faceId.orEmpty()
        }
    }

    fun recognizeTextAndFoundFaceInId(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                val documentScan = async {
                    val resultsList = mutableListOf<String>()
                    val recognizedText = kotlin.runCatching { textRecognitionRepository.recognizeText(uri) }

                        recognizedText
                            .getOrNull()
                            ?.let {
                                it.regions
                                    .map { regions -> regions.lines }
                                    .map { lines -> lines.map { line -> line.words.map { word -> word.text } } }
                                    .forEach { textPack ->
                                        textPack.forEach { words ->
                                            resultsList.addAll(words)
                                        }
                                    }
                            }

                    Log.i("Piotrek", "result of txt  $resultsList")
                }

                val result = withContext(Dispatchers.Default) { faceRecognitionRepository.detectFace(uri) }
                documentScan.await()
                Log.i("Piotrek", "result of face recognition  $result")
                face2 = result.firstOrNull()?.faceId.orEmpty()
            }

        }
    }

    fun compareFaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = faceRecognitionRepository.verifyFaces(face1, face2)

            Log.i("Piotrek", "result of comparing $result")
        }
    }
}