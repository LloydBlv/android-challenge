package ir.zinutech.android.githubclient.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.zinutech.android.githubclient.features.main.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
  @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
  abstract fun contributeMainActivity(): MainActivity
}
