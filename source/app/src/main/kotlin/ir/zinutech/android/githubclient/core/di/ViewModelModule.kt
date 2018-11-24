package ir.zinutech.android.githubclient.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.zinutech.android.githubclient.features.commits.CommitsViewModel


@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(CommitsViewModel::class)
  abstract fun bindCommitsViewModel(commitsViewModel: CommitsViewModel): ViewModel


  @Binds
  abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}