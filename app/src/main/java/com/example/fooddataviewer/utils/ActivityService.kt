package com.example.fooddataviewer.utils

import android.app.Activity

class ActivityService {
    // I create this to navigate between activities

    private var _activity:Activity? = null
    val activity: Activity
        get() = _activity!!

    fun onCreate(activity: Activity){
        this._activity = activity
    }
}