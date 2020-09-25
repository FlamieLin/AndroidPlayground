package com.tdec.androidplayground.utils.sftp

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.util.*

suspend fun downloadSftpFile(
    ftpHost: String,
    ftpPort: Int,
    ftpUserName: String,
    ftpPassword: String,
    ftpPath: String,
    onResult: suspend (message: String) -> Unit
) {
    val session = JSch().getSession(ftpUserName, ftpHost, ftpPort).also { session ->
        session.setPassword(ftpPassword)
        session.timeout = 100000

        Properties().also { config ->
            config["StrictHostKeyChecking"] = "no"
            session.setConfig(config)
        }
    }

    if (!session.isConnected) {
        try {
            session.connect()
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

    val channel = session.openChannel("sftp")

    if (!channel.isConnected) {
        channel.connect()
    }

    val channelSftp = channel as ChannelSftp

    try {
//        val bufferedInputStream = BufferedInputStream(channelSftp.get(ftpPath))
//        val buffer = ByteArray(4096)
//        var len: Int
//        val byteArrayList = ArrayList<Byte>(16)
//        while (bufferedInputStream.read(buffer).also { len = it } >= 0) {
//            byteArrayList.addAll(buffer.copyOfRange(0, len).toList())
//        }
//        byteArrayList
        withContext(Dispatchers.IO) {
            val inputStream: InputStream = channelSftp.get(ftpPath)
            var line: String?
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "utf-8"))
            val stringBuilder = StringBuilder()
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    stringBuilder.append(it).append("\n")
                }
//                line = line!!.replace("\"", "")
//                val info = line!!.split(",".toRegex()).toTypedArray()
//                println("str:$line")
//                for (i in info.indices) println("str:" + info[i])
            }
            channelSftp.quit()
            channel.disconnect()
            session.disconnect()
            onResult("$stringBuilder")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}