package com.incwave.weather.core.base

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<T: BaseViewModel>: Fragment(), CoroutineScope {

    protected abstract val viewModel: T

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    override fun onDetach() {
        super.onDetach()
        cancel()
    }
}