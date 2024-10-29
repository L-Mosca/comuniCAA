package com.example.comunicaa.screens.home

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentHomeBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.screens.home.adapter.HomeCategoriesAdapter
import com.example.comunicaa.screens.home.adapter.HomeUserCardsAdapter
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.navigate
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    override val viewModel: HomeViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    private val adapter = HomeCategoriesAdapter()
    private val userCardsAdapter = HomeUserCardsAdapter()
    private var mediaPlayer: MediaPlayer? = null
    private var isReproducing = false

    override fun initViews() {
        setupBackAction()
        setupDrawer()

        viewModel.fetchCategories()
        viewModel.fetchUserCategories()
    }

    override fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { binding.piHome.isVisible = it }

        viewModel.categories.observe(viewLifecycleOwner) { setupAdapter(it) }

        viewModel.userCategories.observe(viewLifecycleOwner) { setupUserCardsAdapter(it) }
    }

    private fun setupBackAction() {
        onBackPressed(
            action = { requireActivity().moveTaskToBack(true) },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }

    private fun setupDrawer() {
        binding.includeHomeToolbar.apply {
            ivListBar.setOnClickListener { hostViewModel.showDrawer() }
            tvListBar.text = getString(R.string.app_name)
        }

    }

    private fun setupAdapter(categories: List<Category>) {
        adapter.onSubcategorySelected = {
            val direction = HomeFragmentDirections.actionHomeFragmentToActionListFragment(it)
            navigate(direction)
        }

        binding.rvHome.adapter = adapter
        adapter.submitList(categories)
    }

    private fun setupUserCardsAdapter(actions: List<ActionCard>) {
        binding.apply {
            rvHomeUserCards.isVisible = actions.isNotEmpty()
            tvCategoryName.isVisible = actions.isNotEmpty()
            rvHomeUserCards.adapter = userCardsAdapter
        }

        userCardsAdapter.onCardSelected = {
            if (!isReproducing) {
                isReproducing = true
                mediaPlayer = MediaPlayer().apply {
                    setVolume(1f, 1f)
                    try {
                        setDataSource(requireContext(), Uri.parse(it.sound))
                        prepareAsync()
                        setOnPreparedListener { start() }
                        setOnCompletionListener {
                            release()
                            mediaPlayer = null
                            isReproducing = false
                        }
                    } catch (e: IOException) {
                        Log.e("AudioRecord", "prepare() failed")
                    }
                }
            }
        }
        userCardsAdapter.submitList(actions)
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