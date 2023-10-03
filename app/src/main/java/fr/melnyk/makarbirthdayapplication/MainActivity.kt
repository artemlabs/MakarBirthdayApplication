package fr.melnyk.makarbirthdayapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.melnyk.makarbirthdayapplication.ui.theme.MakarBirthdayApplicationTheme

class MainActivity : ComponentActivity() {
    private var isWifiConnected by mutableStateOf(false)
    private var isMobileConnected by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MakarBirthdayApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", isWifiConnected, isMobileConnected)
                }
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Initial network status check
        checkNetworkStatus(connectivityManager)

        // Register a NetworkCallback to listen for network changes
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                checkNetworkStatus(connectivityManager)
            }

            override fun onLost(network: Network) {
                checkNetworkStatus(connectivityManager)
            }
        }

        val networkRequest = android.net.NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun checkNetworkStatus(connectivityManager: ConnectivityManager) {
        isWifiConnected = isNetworkConnected(connectivityManager, ConnectivityManager.TYPE_WIFI)
        isMobileConnected = isNetworkConnected(connectivityManager, ConnectivityManager.TYPE_MOBILE)
    }

    private fun isNetworkConnected(connectivityManager: ConnectivityManager, networkType: Int): Boolean {
        return connectivityManager.allNetworks.any { network ->
            connectivityManager.getNetworkInfo(network)?.type == networkType
        }
    }
}

@Composable
fun Greeting(name: String, isWifiConnected: Boolean, isMobileConnected: Boolean) {
    Text(
        text = "Hello $name! You are connected to:\n" +
                (if (isWifiConnected) "Wi-Fi\n" else "") +
                (if (isMobileConnected) "Mobile Network\n" else ""),
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MakarBirthdayApplicationTheme {
        Greeting("Android", true, false)
    }
}
