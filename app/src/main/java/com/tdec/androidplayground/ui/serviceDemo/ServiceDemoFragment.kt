package com.tdec.androidplayground.ui.serviceDemo

import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tdec.androidplayground.databinding.ServiceDemoFragmentBinding
import com.tdec.androidplayground.ui.serviceDemo.myService.PrintService
import com.tdec.androidplayground.ui.serviceDemo.myService.PrintServiceConnection

class ServiceDemoFragment : Fragment() {

    private val viewModel: ServiceDemoViewModel by viewModels()

    private lateinit var intent: Intent
    private val connection = PrintServiceConnection()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ServiceDemoFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

        viewModel.startService.observe(viewLifecycleOwner, Observer {
            if (it) {
                intent = Intent(requireActivity(), PrintService::class.java)
                requireActivity().bindService(intent, connection, BIND_AUTO_CREATE)
            }
        })

        viewModel.stopService.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (this::intent.isInitialized) {
                    requireActivity().unbindService(connection)
                }
            }
        })

        viewModel.sendMessage.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.message.value?.let { msg ->
                    connection.binder?.setMessage(msg)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::intent.isInitialized) {
            requireActivity().unbindService(connection)
        }
    }

}