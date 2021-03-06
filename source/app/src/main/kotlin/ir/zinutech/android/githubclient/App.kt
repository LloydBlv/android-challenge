package ir.zinutech.android.githubclient

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ir.zinutech.android.githubclient.core.di.AppInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    AppInjector.init(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}