package com.mahmodhota.worldfood3dadventure.data.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Orchestrates SFX and Music.
 */
class AudioManager(private val context: Context) {
    
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        ).build()

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusicType: MusicType = MusicType.NONE
    
    private var musicVolume: Float = 1.0f
    private var sfxVolume: Float = 1.0f
    private val scope = CoroutineScope(Dispatchers.Main)
    private var fadeJob: kotlinx.coroutines.Job? = null

    fun setMusicVolume(volume: Float) {
        musicVolume = volume
        try {
            mediaPlayer?.setVolume(volume, volume)
        } catch (e: Exception) { /* Released */ }
    }

    fun setSfxVolume(volume: Float) {
        sfxVolume = volume
    }

    /**
     * Plays a sound effect.
     */
    fun playSfx(type: SfxType) {
        val resId = SoundRepository.sfxMap[type] ?: return
        if (resId < 1000) return // Skip placeholder IDs

        try {
            soundPool.load(context, resId, 1)
            soundPool.setOnLoadCompleteListener { pool, sampleId, status ->
                if (status == 0) pool.play(sampleId, sfxVolume, sfxVolume, 1, 0, 1f)
            }
        } catch (e: Exception) {
            // Ignore missing resource in dev
        }
    }

    /**
     * Fades into a new music track.
     */
    fun playMusic(type: MusicType) {
        if (currentMusicType == type) return
        
        fadeJob?.cancel()
        fadeJob = scope.launch {
            // Fade out current
            fadeOut()
            
            mediaPlayer?.release()
            mediaPlayer = null
            currentMusicType = type
            
            if (type == MusicType.NONE) return@launch

            val resId = SoundRepository.musicMap[type] ?: return@launch
            if (resId < 1000) return@launch // Skip placeholder IDs

            try {
                mediaPlayer = MediaPlayer.create(context, resId)?.apply {
                    isLooping = true
                    setVolume(0f, 0f)
                    start()
                    fadeIn()
                }
            } catch (e: Exception) {
                // Ignore missing placeholder resource
            }
        }
    }

    private suspend fun fadeOut() {
        val player = mediaPlayer ?: return
        var vol = musicVolume
        try {
            while (vol > 0f) {
                vol -= 0.1f
                player.setVolume(vol.coerceAtLeast(0f), vol.coerceAtLeast(0f))
                delay(50L)
            }
            player.pause()
        } catch (e: Exception) {
            // Player might have been released
        }
    }

    private suspend fun fadeIn() {
        val player = mediaPlayer ?: return
        var vol = 0f
        try {
            while (vol < musicVolume) {
                vol += 0.1f
                player.setVolume(vol.coerceAtMost(musicVolume), vol.coerceAtMost(musicVolume))
                delay(50L)
            }
        } catch (e: Exception) {
            // Player might have been released
        }
    }

    fun release() {
        soundPool.release()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
