package com.example.huertaurbana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.huertaurbana.ui.theme.HuertaUrbanaTheme
import com.example.huertaurbana.ui.today.TodayScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuertaUrbanaTheme { TodayScreen() }
        }
    }
}
