package dev.vinigouveia.factsnorris.shared.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.service.CategoriesService
import dev.vinigouveia.factsnorris.shared.service.FactsService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Vinicius Gouveia on 27/06/2021
 */

private const val TIMEOUT_REQUEST_TIME = 60L

val moshiModule = module {
    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}

val retrofitModule = module {
    single {
        val apiUrl = androidContext().getString(R.string.api_base_url)
        val moshi = get<Moshi>()

        OkHttpClient.Builder()
            .readTimeout(TIMEOUT_REQUEST_TIME, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_REQUEST_TIME, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_REQUEST_TIME, TimeUnit.SECONDS)
            .build()
            .let { client ->
                Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }
    }
}

val serviceModule = module {
    single { get<Retrofit>().create(FactsService::class.java) }
    single { get<Retrofit>().create(CategoriesService::class.java) }
}

val networkModules = listOf(moshiModule, retrofitModule, serviceModule)
