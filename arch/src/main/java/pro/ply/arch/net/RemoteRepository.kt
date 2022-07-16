package pro.ply.arch.net

import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import javax.inject.Singleton

abstract class RemoteRepository<Service> {
    @Inject
    lateinit var serviceFactory: ServiceFactory
    protected val service: Service by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val klass = type.actualTypeArguments[0] as Class<Service>
        return@lazy serviceFactory.create(klass)
    }
}

@Singleton
class ServiceFactory @Inject constructor(var retrofit: Retrofit) {
    fun <Service> create(service: Class<Service>): Service {
        return retrofit.create(service)
    }
}
