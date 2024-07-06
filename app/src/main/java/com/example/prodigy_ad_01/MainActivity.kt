package com.example.prodigy_ad_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prodigy_ad_01.ui.theme.PRODIGY_AD_01Theme

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "Calc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRODIGY_AD_01Theme {
                CalculatorFrame()
            }
        }
    }
}


