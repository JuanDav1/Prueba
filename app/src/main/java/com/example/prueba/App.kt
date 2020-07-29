package com.example.prueba

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.prueba.dagger.AppComponent
import com.example.prueba.dagger.AppModule
import com.example.prueba.dagger.DaggerAppComponent


import com.example.prueba.dagger.UtilsModule

class App : Application(), Application.ActivityLifecycleCallbacks  {
    private var numStarted = 0
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(this)
       appComponent  = DaggerAppComponent.builder().appModule(AppModule(this)).utilsModule(UtilsModule())
           .build()

    }

    fun getAppComponent() : AppComponent {
        return appComponent
    }



    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }


    override fun onActivityStarted(activity: Activity) {
        if (numStarted == 0) {
            // app went to foreground

        }
        numStarted++
    }


    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }
    override fun onActivityStopped(activity: Activity) {
        numStarted--
        if (numStarted == 0) {
            // app went to background
        }}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }



    companion object {

        var context: App? = null
            private set
    }




}
