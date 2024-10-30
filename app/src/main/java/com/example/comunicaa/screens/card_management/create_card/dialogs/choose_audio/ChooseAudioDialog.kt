package com.example.comunicaa.screens.card_management.create_card.dialogs.choose_audio

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseDialog
import com.example.comunicaa.databinding.DialogChooseAudioBinding
import com.example.comunicaa.utils.getAudioFileName
import com.example.comunicaa.utils.helper.AudioHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseAudioDialog : BaseDialog<DialogChooseAudioBinding>() {
    override val bindingInflater: (LayoutInflater) -> DialogChooseAudioBinding =
        DialogChooseAudioBinding::inflate
    override val viewModel: ChooseAudioViewModel by viewModels()

    companion object {
        private const val CHOOSE_AUDIO_DIALOG_EXTRAS = "ChooseAudioDialog.audioData"
        private const val CHOOSE_AUDIO_URL_DIALOG_EXTRAS = "ChooseAudioDialog.audioUrl"

        fun newInstance(audioData: String?, audioUrl: String?): ChooseAudioDialog {
            return ChooseAudioDialog().apply {
                arguments = Bundle().apply {
                    putString(CHOOSE_AUDIO_DIALOG_EXTRAS, audioData)
                    putString(CHOOSE_AUDIO_URL_DIALOG_EXTRAS, audioUrl)
                }
            }
        }
    }

    private lateinit var audioHelper: AudioHelper
    var onAudioSelected: ((String?, String?) -> Unit)? = null
    private var audioUrl: String? = null

    override fun initViews() {
        audioHelper = AudioHelper(requireContext())
        setupListener()
    }

    override fun initObservers() {
        viewModel.buttonStyle.observe(viewLifecycleOwner) { (buttonIcon, buttonText) ->
            binding.btRecordAudio.apply {
                text = getString(buttonText)
                icon = ContextCompat.getDrawable(requireContext(), buttonIcon)
            }
        }

        viewModel.audioUrl.observe(viewLifecycleOwner) {
            it?.let { audio ->
                audioHelper.audioUrl = audio
                afterRecordingDesign()
            }
        }

        viewModel.setInitialPath.observe(viewLifecycleOwner) {
            it?.let { initialData ->
                audioHelper.audioFilePath = initialData
                afterRecordingDesign()
            }
        }
    }

    override fun getBundleArguments() {
        audioUrl = arguments?.getString(CHOOSE_AUDIO_URL_DIALOG_EXTRAS)
        viewModel.handleInitialData(arguments?.getString(CHOOSE_AUDIO_DIALOG_EXTRAS), audioUrl)
    }

    private fun setupListener() {
        binding.apply {
            rlChooseAudio.setOnClickListener { dismiss() }
            cvChooseAudio.setOnClickListener { }
            ivDeleteAudioPreview.setOnClickListener { beforeRecordingDesign() }

            cvAudioPreview.setOnClickListener {
                audioHelper.playRecording(
                    onStart = { binding.ivPreviewAudioReproduce.setImageResource(R.drawable.ic_reproducing) },
                    onComplete = { binding.ivPreviewAudioReproduce.setImageResource(R.drawable.ic_play) },
                )
            }

            ivConfirmAudio.setOnClickListener {
                audioHelper.audioFilePath.let {
                    onAudioSelected?.invoke(it, it?.getAudioFileName())
                }
            }

            btRecordAudio.setOnClickListener {
                val recording = btRecordAudio.text == getString(R.string.record_audio)
                viewModel.changeButtonAppearance(recording)

                if (recording) audioHelper.startRecording { afterRecordingDesign() }
                else audioHelper.stopRecording { afterRecordingDesign() }
            }
        }
    }

    private fun beforeRecordingDesign() {
        binding.apply {
            cvAudioPreview.isVisible = false
            btRecordAudio.isVisible = true
            tvAudioMaxTime.isVisible = true
            ivConfirmAudio.isVisible = false
            tvUseThisAudio.isVisible = false
        }
    }

    private fun afterRecordingDesign() {
        binding.apply {
            val audioName = audioHelper.audioFilePath?.getAudioFileName() ?: "comunicaa_aud_000010.mp3"
            binding.tvPreviewAudioName.text = audioName

            cvAudioPreview.isVisible = true
            btRecordAudio.isVisible = false
            btRecordAudio.text = getString(R.string.record_audio)
            btRecordAudio.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_mic)
            tvAudioMaxTime.isVisible = false
            ivConfirmAudio.isVisible = true
            tvUseThisAudio.isVisible = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioHelper.destroy()
    }

    override fun onStop() {
        super.onStop()
        audioHelper.stop()
    }
}