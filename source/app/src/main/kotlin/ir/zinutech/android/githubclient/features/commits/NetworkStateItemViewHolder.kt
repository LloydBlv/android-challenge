package ir.zinutech.android.githubclient.features.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.zinutech.android.githubclient.R
import ir.zinutech.android.githubclient.core.exception.Failure.NetworkConnection
import ir.zinutech.android.githubclient.core.exception.Failure.ServerError
import ir.zinutech.android.githubclient.core.platform.NetworkState
import ir.zinutech.android.githubclient.core.platform.Status.FAILED
import ir.zinutech.android.githubclient.core.platform.Status.RUNNING

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(view: View,
    private val retryCallback: () -> Unit)
  : RecyclerView.ViewHolder(view) {
  private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
  private val retry = view.findViewById<Button>(R.id.retry_button)
  private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

  init {
    retry.setOnClickListener {
      retryCallback()
    }
  }

  fun bindTo(networkState: NetworkState?) {
    progressBar.visibility = toVisibility(
        networkState?.status == RUNNING)
    retry.visibility = toVisibility(
        networkState?.status == FAILED)
    errorMsg.visibility = toVisibility(
        networkState?.failure != null)
    errorMsg.text = when (networkState?.failure) {
      is NetworkConnection -> {
        itemView.resources.getString(R.string.network_issue)
      }

      is ServerError -> {
        itemView.resources.getString(R.string.server_error)
      }

      else -> {
        networkState?.failure?.toString()
      }
    }
  }

  companion object {
    fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
      val view = LayoutInflater.from(parent.context)
          .inflate(R.layout.network_state_item, parent, false)
      return NetworkStateItemViewHolder(view,
          retryCallback)
    }

    fun toVisibility(constraint: Boolean): Int {
      return if (constraint) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }
  }
}
