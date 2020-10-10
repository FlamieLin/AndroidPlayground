package com.tdec.androidplayground.ui.motionLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tdec.androidplayground.databinding.MotionLayoutFragmentBinding

class MotionLayoutFragment : Fragment() {

    private val viewModel: MotionLayoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MotionLayoutFragmentBinding.inflate(inflater, container, false).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}