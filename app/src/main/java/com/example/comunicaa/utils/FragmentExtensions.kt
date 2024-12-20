package com.example.comunicaa.utils

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets.Type
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.comunicaa.BuildConfig
import com.example.comunicaa.R
import java.io.File
import java.io.IOException
import java.util.Calendar
import java.util.UUID

enum class TransitionAnimation {
    TRANSLATE_FROM_RIGHT, TRANSLATE_FROM_DOWN, TRANSLATE_FROM_LEFT, TRANSLATE_FROM_UP, NO_ANIMATION, TRANSLATE_FROM_DOWN_POP, FADE
}

fun Fragment.navigate(
    directions: NavDirections,
    animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT,
    popUpTo: Int? = null,
    clearBackStack: Boolean? = false,
    sharedElements: Pair<View, String>? = null
) {
    val navController = NavHostFragment.findNavController(this)
    val destinationId = if (clearBackStack == true) navController.graph.id else popUpTo
    val transitionAnimation = if (sharedElements == null) animation else null
    val options = buildOptions(transitionAnimation, clearBackStack, destinationId)
    val extras = sharedElements?.let {
        FragmentNavigatorExtras(it)
    }

    navController.navigate(directions.actionId, directions.arguments, options, extras)
}

private fun buildOptions(
    transitionAnimation: TransitionAnimation?, clearBackStack: Boolean?, @IdRes destinationId: Int?
): NavOptions {
    return navOptions {
        anim {
            when (transitionAnimation) {
                TransitionAnimation.TRANSLATE_FROM_RIGHT -> {
                    enter = R.anim.translate_left_enter
                    exit = R.anim.translate_left_exit
                    popEnter = R.anim.translate_right_enter
                    popExit = R.anim.translate_right_exit
                }

                TransitionAnimation.TRANSLATE_FROM_DOWN -> {
                    enter = R.anim.translate_slide_bottom_up
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_slide_bottom_down
                }

                TransitionAnimation.TRANSLATE_FROM_LEFT -> {
                    enter = R.anim.translate_right_enter
                    exit = R.anim.translate_right_exit
                    popEnter = R.anim.translate_left_enter
                    popExit = R.anim.translate_left_exit
                }

                TransitionAnimation.TRANSLATE_FROM_UP -> {
                    enter = R.anim.translate_slide_bottom_down
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_slide_bottom_up
                }

                TransitionAnimation.NO_ANIMATION -> {
                    enter = R.anim.translate_no_change
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_no_change
                }

                TransitionAnimation.FADE -> {
                    enter = R.anim.translate_fade_in
                    exit = R.anim.translate_fade_out
                    popEnter = R.anim.translate_fade_in
                    popExit = R.anim.translate_fade_out
                }

                TransitionAnimation.TRANSLATE_FROM_DOWN_POP -> {
                    enter = R.anim.translate_slide_bottom_right
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_slide_bottom_down
                }

                else -> {
                }
            }
        }

        // To clean the stack below the current fragment,
        // you must set the 'destinationId' = navGraphId and 'inclusive' = true
        destinationId?.let {
            popUpTo(destinationId) {
                inclusive = clearBackStack == true
            }
        }
    }
}

fun Fragment.hideKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.showKeyboard(view: View) {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.setStatusBarColor(color: Int) {
    val windows = requireActivity().window
    windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    windows.statusBarColor = color
}

fun Fragment.hasDevicePassword(): Boolean {
    val keyguardManager =
        requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return keyguardManager.isKeyguardSecure
}

fun getColor(@AttrRes resId: Int, context: Context): Int {
    val outTypedValue = TypedValue()
    context.theme?.resolveAttribute(resId, outTypedValue, true)
    return outTypedValue.data
}

fun Fragment.onBackPressed(
    action: () -> Unit,
    closeDrawer: () -> Unit,
    drawerIsOpen: () -> Boolean
) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (drawerIsOpen()) closeDrawer()
            else action()
        }
    }
    callback.isEnabled
    activity?.onBackPressedDispatcher?.addCallback(this, callback)
}

fun Fragment.onBackPressed(action: () -> Unit) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            action()
        }
    }
    callback.isEnabled
    activity?.onBackPressedDispatcher?.addCallback(this, callback)
}

fun delayed(action: () -> Unit, duration: Long = 500L) {
    Handler(Looper.getMainLooper()).postDelayed({ action() }, duration)
}


fun Fragment.hideSystemBars() {
    val windowInsetsController =
        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        )
    // Configure the behavior of the hidden system bars.
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    // Add a listener to update the behavior of the toggle fullscreen button when
    // the system bars are hidden or revealed.
    ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { view, windowInsets ->
        // You can hide the caption bar even when the other system bars are visible.
        // To account for this, explicitly check the visibility of navigationBars()
        // and statusBars() rather than checking the visibility of systemBars().
        if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
            || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
        ) {
            /*binding.toggleFullscreenButton.setOnClickListener {
                // Hide both the status bar and the navigation bar.
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }*/
        } else {
            /*binding.toggleFullscreenButton.setOnClickListener {
                // Show both the status bar and the navigation bar.
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            }*/
        }
        ViewCompat.onApplyWindowInsets(view, windowInsets)
    }
}

@SuppressLint("WrongConstant")
@RequiresApi(Build.VERSION_CODES.R)
fun Fragment.showSystemBars() {
    val windowInsetsController =
        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        )

    windowInsetsController.show(Type.systemBars())
}

fun Fragment.createFile(): File {
    val fileName = "temp_image"
    val storageDir = requireActivity().getExternalFilesDir(null)
    return File.createTempFile(fileName, ".jpg", storageDir)
}

fun Fragment.getImageUri(file: File): Uri {
    return FileProvider.getUriForFile(
        requireContext(),
        "${requireActivity().application.packageName}.provider",
        file
    )
}

fun Context.createAudioFilePath(): String? {
    val prefix = "${BuildConfig.NAME}_${Calendar.getInstance().timeInMillis}"
    val audioFile: File? = try {
        val storageDir = this.getExternalFilesDir(null)
        File.createTempFile(prefix, ".mp3", storageDir)
    } catch (ex: IOException) {
        null
    }
    return audioFile?.absolutePath
}

fun String.getAudioFileName(): String {
    val file = File(this)
    return file.name.substring(0, 24)
}

fun String.getAudioDuration(mediaPlayer: MediaPlayer): String {
    mediaPlayer.setDataSource(this)
    mediaPlayer.prepare()
    val duration = mediaPlayer.duration.toLong()
    mediaPlayer.release()
    return duration.toString()
}
