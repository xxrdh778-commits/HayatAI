package com.hayat.ai.voice

import android.content.Context
import android.content.Intent
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class VoiceRecognizer(
    context: Context,
    private val onResult: (String) -> Unit,
    private val onPartialResult: (String) -> Unit,
    private val onError: (Int) -> Unit
) {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
    }

    init {
        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: android.os.Bundle?) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                onError(error)
            }

            override fun onResults(results: android.os.Bundle?) {
                val text = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()

                if (!text.isNullOrBlank()) {
                    onResult(text)
                }
            }

            override fun onPartialResults(partialResults: android.os.Bundle?) {
                val text = partialResults
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull()

                if (!text.isNullOrBlank()) {
                    onPartialResult(text)
                }
            }

            override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
        })
    }

    fun start() {
        recognizer.startListening(intent)
    }

    fun stop() {
        recognizer.stopListening()
    }

    fun cancel() {
        recognizer.cancel()
    }

    fun destroy() {
        recognizer.destroy()
    }
}
