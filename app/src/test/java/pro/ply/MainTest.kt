package pro.ply

import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert
import org.junit.Test

/**
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainTest {
    @Test
    fun okhttp_test() {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://www.baidu.com").get().build()
        val response = client.newCall(request).execute()
        Assert.assertTrue(response.isSuccessful)
        Assert.assertNotNull(response.body)
        Assert.assertEquals(response.code, 200)
        println(response.body?.string())
    }

    @Test
    fun observable_test() {
        Observable.just("Hello World!").subscribe { println(it) }
    }
}
