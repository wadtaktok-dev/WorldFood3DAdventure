package com.mahmodhota.worldfood3dadventure

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

internal fun installAppCheckProvider() {
    Log.d("AppCheck", "Installing DebugAppCheckProvider")
    try {
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
        Log.d("AppCheck", "DebugAppCheckProvider installed")
        
        // Force token generation for verification
        Firebase.appCheck.getAppCheckToken(false).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AppCheck", "Token generation successful")
            } else {
                Log.e("AppCheck", "Token generation failed", task.exception)
            }
        }
    } catch (e: Exception) {
        Log.e("AppCheck", "Failed to install DebugAppCheckProvider", e)
    }
}
