package com.example.comunicaa.screens.host

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.comunicaa.R
import com.example.comunicaa.databinding.ActivityHostBinding
import com.example.comunicaa.databinding.HeaderLayoutBinding
import com.example.comunicaa.domain.models.user.UserModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var headerBinding: HeaderLayoutBinding

    private val viewModel: HostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
    }

    private fun initView() {
        viewModel.getUserData()
        setupViewPadding()
        setupHeader()
        setupNavigation()
        setupDrawer()
    }

    private fun initObservers() {
        viewModel.openDrawer.observe(this) { binding.drawerLayout.openDrawer(GravityCompat.START) }
        viewModel.closeDrawer.observe(this) { binding.drawerLayout.closeDrawers() }
        viewModel.graphId.observe(this) { navController.navigate(it) }

        viewModel.user.observe(this) { updateUserOnDrawer(it) }
        viewModel.changeScreen.observe(this) { navController.navigate(it) }
        viewModel.logoutSuccess.observe(this) { updateUserOnDrawer(null) }
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
                viewModel.handleNavigation(item)
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

        headerBinding.includeHeaderLoggedMessage.apply {
            btLogin.setOnClickListener {
                navController.navigate(R.id.auth_nav_graph)
                binding.drawerLayout.closeDrawers()
            }

            headerBinding.vDrawerBack.setOnClickListener { binding.drawerLayout.closeDrawers() }
        }

    }

    private fun updateUserOnDrawer(user: UserModel?) {
        headerBinding.includeHeaderUserData.llHeaderUserData.isVisible = user != null
        headerBinding.includeHeaderLoggedMessage.clLoggedMessage.isVisible = user == null

        headerBinding.includeHeaderUserData.apply {
            tvDrawerUserName.text = user?.displayName
            tvDrawerUserEmail.text = user?.email
            tvDrawerUserPhone.text = user?.phoneNumber
            user?.photoUrl?.let {  Picasso.get().load(it).into(ivDrawerUserImage)  }

        }
    }

    private fun setupViewPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
    }
}