package com.tdec.androidplayground.ui.jsch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdec.androidplayground.utils.sftp.downloadSftpFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class JschViewModel : ViewModel() {

    private val _message = MutableLiveData<String>("")
    val message: LiveData<String> = _message

    fun onCleanClick() {
        _message.value = ""
    }

    fun onDownloadClick() {
        viewModelScope.launch(Dispatchers.IO) {
            downloadSftpFile(
                "192.168.0.120",
                22,
                "rick",
                "123456",
                "text.txt"
            ) { str ->
                val calendar = Calendar.getInstance()
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val min = calendar[Calendar.MINUTE]
                val sec = calendar[Calendar.SECOND]
                val milliSec = calendar[Calendar.MILLISECOND]
                withContext(Dispatchers.Main) {
                    _message.value += "[${hour}:${min}:${sec}:${milliSec}]: ${str}\n"
                }
            }
        }
    }
}