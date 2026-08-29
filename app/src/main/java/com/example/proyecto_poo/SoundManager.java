package com.example.proyecto_poo;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;

public class SoundManager {
    private static MediaPlayer mediaPlayer;

    public static void iniciarMusica(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.musica_fondo);
            if (mediaPlayer != null) {
                // Configuración de audio para evitar distorsión
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_GAME)
                                .build()
                );
                mediaPlayer.setLooping(true);
                mediaPlayer.setVolume(0.25f, 0.25f); // 25% de volumen para evitar saturación
            }
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static void pausarMusica() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void detenerMusica() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}