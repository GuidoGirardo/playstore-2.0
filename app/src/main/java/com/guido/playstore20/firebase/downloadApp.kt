package com.guido.playstore20.firebase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

fun downloadApk(apkUrl: String, appName: String, context: Context, logo: String) {
    // Obtener el directorio de descargas predeterminado del dispositivo
    val downloadDirectory = Environment.DIRECTORY_DOWNLOADS

    // Crear una solicitud de descarga
    val request = DownloadManager.Request(Uri.parse(apkUrl))

    // Configurar la solicitud de descarga
    request.setTitle("$appName Download")
    request.setDescription("Downloading $appName")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setAllowedOverMetered(true) // Permitir descargas en conexiones móviles
    request.setAllowedOverRoaming(true) // Permitir descargas en roaming

    // Establecer la ubicación de destino en el directorio de descargas
    request.setDestinationInExternalPublicDir(downloadDirectory, "$appName.apk")

    // Obtener el servicio de DownloadManager y encolar la solicitud
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}