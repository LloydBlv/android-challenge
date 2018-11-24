package ir.zinutech.android.githubclient.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.zinutech.android.githubclient.features.commits.CommitsFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
  @ContributesAndroidInjector
  abstract fun contributeCommitsFragment(): CommitsFragment
}