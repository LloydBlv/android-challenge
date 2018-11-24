package ir.zinutech.android.githubclient.features.commits

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.zinutech.android.githubclient.R
import ir.zinutech.android.githubclient.core.extension.inflate
import ir.zinutech.android.githubclient.core.platform.NetworkState
import ir.zinutech.android.githubclient.core.utils.StringUtils
import kotlinx.android.synthetic.main.item_commit_layout.view.commit_item_content
import kotlinx.android.synthetic.main.item_commit_layout.view.commit_item_time
import kotlinx.android.synthetic.main.item_commit_layout.view.commit_item_title

class CommitsListAdapter(
    private val retryCallback: () -> Unit,
    private val onListItemClickListener: (View, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


  val data: ArrayList<Commit> = arrayListOf()
  private var networkState: NetworkState? = null

  override fun getItemCount(): Int = data.size + if (hasExtraRow()) 1 else 0


  fun getItem(itemView: View, recyclerView: RecyclerView?): Commit? {
    return recyclerView?.getChildAdapterPosition(
        itemView)?.takeIf { it != RecyclerView.NO_POSITION }?.let {
      data[it]
    }
  }

  fun swapData(newData: List<Commit>) {
    val diffCallback = CommitsDiffCallback(this.data, newData)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.data.clear()
    this.data.addAll(newData)
    diffResult.dispatchUpdatesTo(this)
  }


  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (getItemViewType(position)) {
      R.layout.item_commit_layout -> (holder as CommitViewHolder).bind(data[position])
      R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
          networkState)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      R.layout.item_commit_layout -> CommitViewHolder(
          parent.inflate(R.layout.item_commit_layout).apply {
            setOnClickListener {
              onListItemClickListener.invoke(it, viewType)
            }
          })
      R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
      else -> throw IllegalArgumentException("unknown view type $viewType")
    }
  }

  private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED


  override fun getItemViewType(position: Int): Int {
    return if (hasExtraRow() && position == itemCount - 1) {
      R.layout.network_state_item
    } else {
      R.layout.item_commit_layout
    }
  }


  fun setNetworkState(newNetworkState: NetworkState?) {
    val previousState = this.networkState
    val hadExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val hasExtraRow = hasExtraRow()
    if (hadExtraRow != hasExtraRow) {
      if (hadExtraRow) {
        notifyItemRemoved(data.size)
      } else {
        notifyItemInserted(data.size)
      }
    } else if (hasExtraRow && previousState != newNetworkState) {
      notifyItemChanged(itemCount - 1)
    }
  }

  class CommitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(commit: Commit?) {
      commit ?: return

      itemView.commit_item_title.text = commit.author.name
      itemView.commit_item_content.text = commit.message
      itemView.commit_item_time.text = StringUtils.formatDate(commit.author.date)

    }
  }
}