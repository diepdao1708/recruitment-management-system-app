package com.android.recruitment.features.resume_detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.recruitment.R
import com.android.recruitment.databinding.FragmentResumeDetailBinding
import com.android.recruitment.features.resume.ItemResume
import com.android.recruitment.utils.Constant
import com.android.recruitment.utils.DocumentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeDetailFragment : Fragment() {

    private lateinit var binding: FragmentResumeDetailBinding
    private val viewModel: ResumeDetailViewModel by viewModels()
    private var uri: Uri? = null
    private var itemResume: ItemResume? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResumeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constant.RESUME_TO_DETAIL_KEY, Uri::class.java)
        } else {
            arguments?.getParcelable(Constant.RESUME_TO_DETAIL_KEY) as Uri?
        }

        itemResume = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(Constant.RESUME_TO_DETAIL_KEY, ItemResume::class.java)
        } else {
            arguments?.getSerializable(Constant.RESUME_TO_DETAIL_KEY) as ItemResume?
        }

        binding.tvUploadCV.isVisible = itemResume == null
        binding.pdfView.isVisible = itemResume == null
        binding.webView.isVisible = itemResume != null

        if (itemResume?.path != null) {
            Log.e("xxxx", "path resume: ${Constant.BASE_URL + "/static/" + itemResume?.name}")
            binding.webView.loadUrl("https://drive.google.com/file/d/1Jqd6epSh-CRT32NpqEB57nOv9lYVfdCX/view?usp=sharing")
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.setSupportZoom(true)
        }

        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()

        onClick()
        observer()
    }

    private fun observer() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                ResumeDetailViewModel.Event.BackToResume -> {
                    findNavController().navigate(R.id.action_resumeDetailFragment_to_resumeFragment)
                }

                else -> {
                    // noop
                }
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resumeDetailFragment_to_resumeFragment)
        }

        binding.tvUploadCV.setOnClickListener {
            if (uri != null) {
                val file = DocumentUtils.getFile(activity, uri!!)
                viewModel.uploadResume(file)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_resumeDetailFragment_to_resumeFragment)
                }
            })
    }
}