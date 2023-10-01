package fr.melnyk.makarbirthdayapplication

import android.content.Context
import android.net.ConnectivityManager
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MakarBirthdayApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val connectivityManager =
                        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val isWifiConn = isNetworkConnected(connectivityManager, ConnectivityManager.TYPE_WIFI)
                    val isMobileConn = isNetworkConnected(connectivityManager, ConnectivityManager.TYPE_MOBILE)

                    Greeting("Android", isWifiConn, isMobileConn)
                }
            }
        }
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
