package ir.zinutech.android.githubclient.core.platform

import android.content.Context
import ir.zinutech.android.githubclient.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
  val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}