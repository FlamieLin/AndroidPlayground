package com.tdec.androidplayground.ui.serviceDemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ServiceDemoViewModel : ViewModel() {

    val message by lazy {
        MutableLiveData<String>("")
    }

    private val _startService = MutableLiveData(false)
    val startService: LiveData<Boolean> = _startService

    private val _stopService = MutableLiveData(false)
    val stopService: LiveData<Boolean> = _stopService

    private val _sendMessage = MutableLiveData(false)
    val sendMessage: LiveData<Boolean> = _sendMessage

    fun onStartServiceClick() {
        _startService.value = true
        _startService.value = false
    }

    fun onStopServiceClick() {
        _stopService.value = true
        _stopService.value = false
    }

    fun onSendClick() {
        _sendMessage.value = true
        _sendMessage.value = false
    }
}