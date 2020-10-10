package com.tdec.androidplayground.ui.serviceDemo.myService

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class PrintServiceConnection: ServiceConnection {

    var binder: PrintService.PrintBinder? = null

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        binder = p1 as PrintService.PrintBinder
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        binder = null
    }
}