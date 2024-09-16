package com.example.comunicaa.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.example.comunicaa.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.NullPointerException

class AnimationUtils {

    companion object {

        /**
         * Animates a view making it translate from the right side of the screen to its original
         * position set in the xml file
         *
         * The handler is necessary to run the animation without further code when called
         *
         * @param view view to be animated
         * @param root view parent to get the width from (binding.root recommended)
         * @param translationMultiplier if set to -1f makes the animation change to a left side translation
         * @param duration duration of the animation
         */
        fun enterTranslateX(
            view: View,
            root: View,
            delay: Long,
            translationMultiplier: Float = 1f,
            duration: Long = 400
        ) {
            Handler(Looper.getMainLooper()).postDelayed({
                view.translationX = root.width.toFloat() * translationMultiplier
                view.isVisible = true
                view.animate()
                    .translationX(0f)
                    .setDuration(duration)
                    .setStartDelay(delay)
                    .start()
            }, 100)
        }

        /**
         * Animates a view making it translate from the bottom side of the screen to its original
         * position set in the xml file
         *
         * The handler is necessary to run the animation without further code when called
         *
         * @param view view to be animated
         * @param root view parent to get the width from (binding.root recommended)
         * @param translationMultiplier if set to -1f makes the animation change to a top side translation
         * @param duration duration of the animation
         */
        fun enterTranslateY(
            view: View,
            root: View,
            delay: Long,
            translationMultiplier: Float = 1f,
            duration: Long = 400
        ) {
            Handler(Looper.getMainLooper()).postDelayed({
                view.translationY = root.height.toFloat() * translationMultiplier
                view.isVisible = true
                view.animate()
                    .translationY(0f)
                    .setDuration(duration)
                    .setStartDelay(delay)
                    .start()
            }, 100)
        }

        fun fadeOut(view: View, duration: Long, onEnd: (() -> Unit) = {}) {
            Handler(Looper.getMainLooper()).postDelayed({
                view.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .withEndAction {
                        //try catch because there was a crash when the user changed screens
                        //while the animation was running (binding on fragment would be null)
                        try {
                            onEnd.invoke()
                        } catch (e: NullPointerException) {
                            e.printStackTrace()
                        }
                    }
                    .start()
            }, 0)
        }

        fun scale(view: View, @ApplicationContext context: Context) {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_scale_action))
        }
    }
}