package pro.ply.gitrepo

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this

        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
        }
    }

    companion object {
        lateinit var application: Context
    }
}
