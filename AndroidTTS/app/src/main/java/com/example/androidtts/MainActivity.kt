package com.example.androidtts

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity() {

    private var textToSpeech : TextToSpeech? = null
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        voiceBtn.setOnClickListener {
            speak()
        }

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("Logd", "1st")
                //사용할 언어를 설정
                val result = textToSpeech?.setLanguage(Locale.KOREAN)
                //이상한 언어 씨부리면
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this@MainActivity, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Logd", "2nd")
                    //음성 톤
                    textToSpeech?.setPitch(2.0f)
                    //읽는 속도
                    textToSpeech?.setSpeechRate(0.6f)
                }
            }
        }
    } //onCreate

    private fun speak() {  //STT
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something")

        try{
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        }catch (e : Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            REQUEST_CODE_SPEECH_INPUT -> {
                if(resultCode == Activity.RESULT_OK && null != data){
                    //음성데이터를 텍스트뷰에 입력
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    textView.text = result!![0]

                    Speech()
                }
            }
        }
    }

    private fun Speech() {  //TTS
        val text = textView.getText().toString()
        Log.d("Logd", "3rd")
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)// API 20
    }
}