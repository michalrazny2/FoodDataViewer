package com.example.fooddataviewer

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


@Suppress("unused")
class AndroidTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, className, context)
    }
}