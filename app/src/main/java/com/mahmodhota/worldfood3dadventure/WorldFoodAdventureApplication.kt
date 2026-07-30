package com.mahmodhota.worldfood3dadventure

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.initialize

class WorldFoodAdventureApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("AppCheck", "Application started")

        try {
            // Initialize Firebase first
            Firebase.initialize(context = this)

            // Install variant-specific App Check provider
            installAppCheckProvider()
        } catch (e: Exception) {
            Log.e("AppCheck", "Error during initialization", e)
        }
    }
}
