package com.sina.core.network.networkListener

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkListener @Inject constructor(@ApplicationContext private val context: Context) : ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(): MutableStateFlow<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)
        var isConnected = false
        connectivityManager.allNetworks.forEach { network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true;return@forEach
                }
            }
        }
        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}