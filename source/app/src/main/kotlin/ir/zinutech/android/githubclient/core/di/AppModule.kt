package ir.zinutech.android.githubclient.core.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import ir.zinutech.android.githubclient.BuildConfig
import ir.zinutech.android.githubclient.features.commits.CommitsApi
import ir.zinutech.android.githubclient.features.commits.CommitsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

  @Provides
  @Singleton
  fun provideApplicationContext(application: Application): Context = application


  @Singleton
  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
  }


  @Singleton
  @Provides
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(
                    if (BuildConfig.DEBUG)
                      HttpLoggingInterceptor.Level.BASIC
                    else
                      HttpLoggingInterceptor.Level.NONE
                )
        )
        .build()
  }

  @Singleton
  @Provides
  fun provideCommitsApi(okHttpClient: OkHttpClient, moshi: Moshi): CommitsApi {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
        .create(CommitsApi::class.java)
  }


  @Provides
  @Singleton
  fun provideCommitsRepository(
      dataSource: CommitsRepository.Network): CommitsRepository = dataSource

}