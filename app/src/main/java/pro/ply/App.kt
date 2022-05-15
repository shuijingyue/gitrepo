package pro.ply

import android.app.Application
import android.content.Context
import pro.ply.ann.Fake

@Fake
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        application = this
    }

    companion object {
        lateinit var application: Context
    }
}
