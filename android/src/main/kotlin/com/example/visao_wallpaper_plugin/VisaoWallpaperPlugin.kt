package com.example.visao_wallpaper_plugin

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Build
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class VisaoWallpaperPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var applicationContext: android.content.Context

    override fun onAttachedToEngine(
        flutterPluginBinding: FlutterPlugin.FlutterPluginBinding
    ) {
        applicationContext = flutterPluginBinding.applicationContext

        channel = MethodChannel(
            flutterPluginBinding.binaryMessenger,
            "com.visaodofuturo.app/wallpaper"
        )

        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(
        call: MethodCall,
        result: Result
    ) {
       when (call.method) {

    "aplicarWallpaper" -> {

        try {
            val bytes = call.argument<ByteArray>("bytes")
            val destino = call.argument<String>("destino")
                ?.trim()
                ?.lowercase()

            if (bytes == null || bytes.isEmpty()) {
                result.error(
                    "IMAGEM_INVALIDA",
                    "Os bytes do wallpaper estão vazios.",
                    null
                )
                return
            }

            if (
                destino != "home" &&
                destino != "lock" &&
                destino != "both"
            ) {
                result.error(
                    "DESTINO_INVALIDO",
                    "Destino inválido: $destino",
                    null
                )
                return
            }

            val bitmap = BitmapFactory.decodeByteArray(
                bytes,
                0,
                bytes.size
            )

            if (bitmap == null) {
                result.error(
                    "IMAGEM_INVALIDA",
                    "Não foi possível decodificar o wallpaper.",
                    null
                )
                return
            }

            val wallpaperManager =
                WallpaperManager.getInstance(applicationContext)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                when (destino) {

                    "home" -> {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                    }

                    "lock" -> {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK
                        )
                    }

                    "both" -> {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )

                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK
                        )
                    }
                }

            } else {

                if (destino == "lock") {
                    bitmap.recycle()

                    result.error(
                        "ANDROID_ANTIGO",
                        "Tela de bloqueio separada requer Android 7 ou superior.",
                        null
                     )
                    return
                }

                wallpaperManager.setBitmap(bitmap)
            }

            bitmap.recycle()
            result.success(true)

        } catch (e: Exception) {
            result.error(
                "ERRO_WALLPAPER",
                e.message ?: "Erro ao aplicar wallpaper.",
                null
            )
        }
    }
              "abrirBateriaSemRestricoes" -> {

    try {

        val intent = Intent(
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        )

        intent.data = Uri.parse(
            "package:${applicationContext.packageName}"
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        applicationContext.startActivity(intent)

        result.success(true)

    } catch (e: Exception) {

        result.error(
            "ERRO_BATERIA",
            e.message ?: "Não foi possível abrir configuração de bateria.",
            null
        )
    }

} // fecha bateria


"abrirDadosSemRestricoes" -> {

    try {

        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        )

        intent.data = Uri.parse(
            "package:${applicationContext.packageName}"
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        applicationContext.startActivity(intent)

        result.success(true)

    } catch (e: Exception) {

        result.error(
            "ERRO_DADOS",
            e.message ?: "Não foi possível abrir configuração de dados.",
            null
        )
    }

}
               

        else -> {
            result.notImplemented()
        }
       }
    }

    override fun onDetachedFromEngine(
        binding: FlutterPlugin.FlutterPluginBinding
    ) {
        channel.setMethodCallHandler(null)
    }
}
