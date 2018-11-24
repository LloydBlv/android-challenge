package ir.zinutech.android.githubclient.features.commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ir.zinutech.android.githubclient.R
import ir.zinutech.android.githubclient.core.di.Injectable
import ir.zinutech.android.githubclient.core.platform.NetworkState
import kotlinx.android.synthetic.main.fragment_commits_layout.commits_fragment_recycler_view
import kotlinx.android.synthetic.main.fragment_commits_layout.commits_fragment_swipe_refresh
import timber.log.Timber
import javax.inject.Inject

class CommitsFragment : Fragment(), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var commitsViewModel: CommitsViewModel

  private var commitsAdapter: CommitsListAdapter? = null


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_commits_layout, container, false)
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    commits_fragment_swipe_refresh.setOnRefreshListener {
      commitsViewModel.refresh()
    }


    commitsAdapter = CommitsListAdapter(retryCallback = {
      commitsViewModel.retry()
    }) { itemView, _ ->
      (commits_fragment_recycler_view.adapter as CommitsListAdapter).getItem(itemView,
          commits_fragment_recycler_view)?.let {
        Timber.d("clickedCommit:[%s]", it)
      }

    }
    commits_fragment_recycler_view.adapter = commitsAdapter
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    commitsViewModel = ViewModelProviders.of(this, viewModelFactory)[CommitsViewModel::class.java]

    commitsViewModel.refreshState.observe(this, Observer {
      commits_fragment_swipe_refresh.isRefreshing = it == NetworkState.LOADING
    })


    commitsViewModel.commits.observe(this, Observer {
      commitsAdapter?.swapData(it)
    })

    commitsViewModel.networkState.observe(this, Observer {
      commitsAdapter?.setNetworkState(it)
    })

    if (savedInstanceState == null) {
      commitsViewModel.loadCommits()
    }

  }
}