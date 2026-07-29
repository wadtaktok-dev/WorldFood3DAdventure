package com.mahmodhota.worldfood3dadventure.data.audio

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Manages haptic feedback (vibration).
 */
class HapticManager(context: Context) {
    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    private var isEnabled: Boolean = true

    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    /**
     * Triggers a light vibration (e.g. tile select).
     */
    fun light() {
        vibrate(50L, 50)
    }

    /**
     * Triggers a medium vibration (e.g. match).
     */
    fun medium() {
        vibrate(100L, 128)
    }

    /**
     * Triggers a heavy vibration (e.g. level win).
     */
    fun heavy() {
        vibrate(200L, 255)
    }

    private fun vibrate(duration: Long, amplitude: Int) {
        if (!isEnabled || !vibrator.hasVibrator()) return

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(duration)
            }
        } catch (e: SecurityException) {
            // Permission missing or restricted
        } catch (e: Exception) {
            // Never crash because of haptic feedback
        }
    }
}
