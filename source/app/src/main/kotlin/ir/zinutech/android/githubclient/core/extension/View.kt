package ir.zinutech.android.githubclient.core.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


fun ViewGroup.inflate(@LayoutRes layoutResId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutResId, this, attachToRoot)
}