package pro.ply

import android.app.Application
import pro.ply.ann.Fake

@Fake
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
