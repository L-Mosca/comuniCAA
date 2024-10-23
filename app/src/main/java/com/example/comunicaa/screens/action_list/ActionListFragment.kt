package com.example.comunicaa.screens.action_list

import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
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
import com.example.comunicaa.utils.toDpMetric
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
    private var mediaPlayer: MediaPlayer? = null

    override fun initViews() {
        setupInitialData()
        setupBackAction()
        viewModel.fetchActions()
    }

    override fun initObservers() {}

    private fun setupBackAction() {
        onBackPressed(
            action = { findNavController().popBackStack() },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }

    private fun setupAdapter(list: List<ActionCard>) {
        adapter.submitList(list)
        binding.rvAction.adapter = adapter

        adapter.onCardPressed = { actionCard ->
            mediaPlayer = MediaPlayer().apply {
                setVolume(1f, 1f)
                try {
                    setDataSource(requireContext(), Uri.parse(actionCard.sound))
                    prepareAsync()
                    setOnPreparedListener { start() }
                } catch (e: IOException) {
                    Log.e("AudioRecord", "prepare() failed")
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
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}