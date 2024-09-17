package com.example.comunicaa.screens.host

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.comunicaa.R
import com.example.comunicaa.databinding.ActivityHostBinding
import com.example.comunicaa.databinding.HeaderLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var headerBinding: HeaderLayoutBinding

    private val viewModel: HostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
    }

    private fun initView() {
        setupHeader()
        setupNavigation()
        setupDrawer()
    }

    private fun initObservers() {
        viewModel.openDrawer.observe(this) { binding.drawerLayout.openDrawer(GravityCompat.START) }
        viewModel.closeDrawer.observe(this) { binding.drawerLayout.closeDrawers() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawer() {
        binding.apply {
            setupDrawerListener()
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navigationView.setNavigationItemSelectedListener { item ->
                if (item.itemId == R.id.teste1) {
                    navController.navigate(R.id.card_nav_graph)
                }
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    private fun setupDrawerListener() {
        toggle = object : ActionBarDrawerToggle(
            this@HostActivity,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                viewModel.changeDrawerStatus(true)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                viewModel.changeDrawerStatus(false)
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupHeader() {
        headerBinding = HeaderLayoutBinding.bind(binding.navigationView.getHeaderView(0))

        headerBinding.btLogin.setOnClickListener {
            navController.navigate(R.id.auth_nav_graph)
            binding.drawerLayout.closeDrawers()
        }
    }
}