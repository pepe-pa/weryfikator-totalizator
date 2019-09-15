package com.hackyeah.orlen.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startNewActivity(activityClass: Class<out AppCompatActivity>, finishCurrent: Boolean = true) {
    startActivity(Intent(this, activityClass))
    if (finishCurrent) {
        finish()
    }
}