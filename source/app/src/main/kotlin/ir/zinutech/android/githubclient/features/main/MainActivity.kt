package ir.zinutech.android.githubclient.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ir.zinutech.android.githubclient.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}
