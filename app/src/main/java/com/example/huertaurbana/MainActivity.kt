package com.example.huertaurbana

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.setContent
import com.example.huertaurbana.ui.theme.HuertaUrbanaTheme
import com.example.huertaurbana.ui.today.TodayScreen
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val locale = Locale("es", "AR")
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration).apply { setLocale(locale) }
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuertaUrbanaTheme { TodayScreen() }
        }
    }
}
