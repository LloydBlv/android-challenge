package ir.zinutech.android.githubclient.features.commits

import androidx.recyclerview.widget.DiffUtil

class CommitsDiffCallback(
    private val oldList: List<Commit>,
    private val newList: List<Commit>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition] == newList[newItemPosition]

  override fun getOldListSize(): Int = oldList.size
  override fun getNewListSize(): Int = newList.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
      oldList[oldItemPosition].sha == newList[newItemPosition].sha
}