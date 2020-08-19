package com.example.fooddataviewer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.fooddataviewer.utils.ActivityService

class Activity : AppCompatActivity() {

    private lateinit var activityService: ActivityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        activityService = applicationContext.component.activityService()
        activityService.onCreate(this)
    }

    override fun onDestroy() {
        activityService.onDestroy(this)
        super.onDestroy()
    }
}