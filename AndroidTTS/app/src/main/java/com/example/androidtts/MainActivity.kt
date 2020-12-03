package com.example.androidtts

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var textToSpeech : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(this, object: TextToSpeech.OnInitListener {

            override fun onInit(status:Int) {
                if (status == TextToSpeech.SUCCESS)
                {
                    Log.d("Logd", "1st")
                    //사용할 언어를 설정
                    val result = textToSpeech?.setLanguage(Locale.KOREAN)
                    //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Toast.makeText(this@MainActivity, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Log.d("Logd", "2nd")
                        tts_Btn.setEnabled(true)
                        //음성 톤
                        textToSpeech?.setPitch(0.7f)
                        //읽는 속도
                        textToSpeech?.setSpeechRate(0.7f)
                    }
                }
            }
        })

        tts_Btn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                Speech()
            }
        })
    }
    private fun Speech() {
        val text = edtSpeech.getText().toString()
        Log.d("Logd", "3rd")
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)// API 20
    }
}