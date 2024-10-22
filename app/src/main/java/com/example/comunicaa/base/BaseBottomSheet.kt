package com.example.comunicaa.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.comunicaa.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("UNCHECKED_CAST")
abstract class BaseBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {

    abstract val bindingInflater: (LayoutInflater) -> VB
    private var viewBinding: ViewBinding? = null
    val binding: VB get() = viewBinding as VB
    abstract val viewModel: BaseViewModel

    /**
     * @author L-Mosca - lucasbvmosca@gmail.com - https://github.com/L-Mosca.
     *
     * <p>This function called into BottomSheetDialogFragment.onViewCreated lifecycle.
     *
     * Use this to fetch initial data and setup layout (click events, recycler view, adapters...)</p>
     */
    abstract fun initViews()

    /**
     * @author L-Mosca - lucasbvmosca@gmail.com - https://github.com/L-Mosca.
     *
     * <p>This function called into BottomSheetDialogFragment.onViewCreated lifecycle.
     *
     * Use this to observer viewModel streams) </p>
     */
    abstract fun initObservers()

    /**
     * @author L-Mosca - lucasbvmosca@gmail.com - https://github.com/L-Mosca.
     *
     * <p>This function called into BottomSheetDialogFragment.onCreate view lifecycle.
     *
     * Use this to get bundle arguments passed by this instance invocation) </p>
     */
    abstract fun getBundleArguments()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = bindingInflater.invoke(inflater)
        return viewBinding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val window = dialog.window
        window?.run {
            setBackgroundDrawableResource(R.color.shadow)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogAnimation)
        getBundleArguments()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }
}