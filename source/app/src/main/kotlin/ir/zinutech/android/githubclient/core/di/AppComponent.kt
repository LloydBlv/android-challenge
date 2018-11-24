package ir.zinutech.android.githubclient.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import ir.zinutech.android.githubclient.App
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
      AndroidInjectionModule::class,
      AndroidSupportInjectionModule::class,
      AppModule::class,
      MainActivityModule::class]
)
interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: App)
}