package com.tdec.androidplayground.ui.serviceDemo.myService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class PrintService : Service() {

    private val tag = "PrintService"

    var message = "服务运行中"

    var job = Job()

    var binder = PrintBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i(tag, "创建服务")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "销毁服务")
        job.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    inner class PrintBinder: Binder() {

        init {
            GlobalScope.launch(Dispatchers.IO + job) {
                while (job.isActive) {
                    Log.i(tag, message)

                    delay(1000)
                }
            }
        }

        fun setMessage(value: String) {
            message = value
        }
    }
}
