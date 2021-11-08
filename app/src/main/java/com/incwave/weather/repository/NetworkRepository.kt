package com.incwave.weather.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class NetworkRepository: CoroutineScope {
    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    private var internetAvailable = false
    private val _isInternetAvailable = MutableLiveData<Boolean?>(null)
    val isInternetAvailable: LiveData<Boolean?> by lazy { _isInternetAvailable }
    private var cm: ConnectivityManager? = null
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            launch {
                isInternetAvailable().apply {
                    val status = this
                    withContext(Dispatchers.Main) {
                        _isInternetAvailable.value = status
                        internetAvailable = status
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            launch {
                withContext(Dispatchers.Main) {
                    _isInternetAvailable.value = false
                    internetAvailable = false
                }
            }
        }

        override fun onAvailable(network: Network) {
            launch {
                isInternetAvailable().apply {
                    val status = this
                    withContext(Dispatchers.Main) {
                        _isInternetAvailable.value = status
                        internetAvailable = status
                    }
                }
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockAddress = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockAddress, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }

    fun registerNetworkCallback(context: Context) {
        if (cm == null) {
            cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        val builder = NetworkRequest.Builder()
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val networkRequest = builder.build()

        cm?.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun unregister() {
        cm?.unregisterNetworkCallback(networkCallback)
    }
}