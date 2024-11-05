package com.example.comunicaa.screens.action_list

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentActionListBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class ActionListFragment : BaseFragment<FragmentActionListBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentActionListBinding =
        FragmentActionListBinding::inflate
    override val viewModel: ActionListViewModel by viewModels()

    private val hostViewModel: HostViewModel by activityViewModels()
    private val navArgs: ActionListFragmentArgs by navArgs()
    private val adapter = ActionListAdapter()
    private lateinit var mediaPlayer: MediaPlayer
    private var isReproducing = false

    override fun initViews() {
        setupInitialData()
        setupBackAction()
    }

    override fun initObservers() {}

    private fun setupBackAction() {
        onBackPressed(
            action = { findNavController().popBackStack() },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapter(list: List<ActionCard>) {
        adapter.submitList(list)
        binding.rvAction.adapter = adapter

        adapter.onCardPressed = { actionCard ->
            if (!isReproducing) {
                adapter.isClickable = false
                isReproducing = true
                adapter.notifyDataSetChanged()
                mediaPlayer.apply {
                    try {
                        setDataSource(requireContext(), Uri.parse(actionCard.sound))
                        prepare()
                        setOnPreparedListener { start() }
                        setOnCompletionListener {
                            isReproducing = false
                            adapter.isClickable = true
                            adapter.notifyDataSetChanged()
                            reset()
                        }
                    } catch (e: IOException) {
                        Log.e("AudioRecord", "prepare() failed")
                        adapter.isClickable = true
                        isReproducing = false
                        adapter.notifyDataSetChanged()
                        reset()
                    }
                }
            }

        }
    }

    private fun setupInitialData() {
        val color = navArgs.subCategory.color ?: Color.WHITE
        val actionList = navArgs.subCategory.actions ?: emptyList()
        val title = navArgs.subCategory.name ?: getString(R.string.app_name)

        binding.clActionList.setBackgroundColor(color)
        binding.includeActionBar.apply {
            tvListBar.text = title
            cvListBar.backgroundTintList = ColorStateList.valueOf(color)
            ivListBar.setOnClickListener { findNavController().popBackStack() }
            ivListBar.setImageResource(R.drawable.ic_back)
        }

        setupAdapter(actionList)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setVolume(1f, 1f)
    }
}