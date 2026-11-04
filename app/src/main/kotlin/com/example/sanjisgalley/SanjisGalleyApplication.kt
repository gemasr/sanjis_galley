package com.example.sanjisgalley

import android.app.Application
import com.example.sanjisgalley.data.di.AppContainer

class SanjisGalleyApplication : Application() {
  val container: AppContainer by lazy { AppContainer() }
}
