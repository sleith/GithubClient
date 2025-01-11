package liem.ray.githubclient.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import liem.ray.githubclient.api.UserApi
import liem.ray.githubclient.api.interactors.UserApiInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val apiModule = module {
  factory { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
  factory {
    GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()
  }

  single {
    OkHttpClient.Builder()
      .addNetworkInterceptor(get<HttpLoggingInterceptor>())
      .connectTimeout(NETWORK_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
      .readTimeout(NETWORK_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
      .build()
  }

  single {
    Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(get()))
      .client(get())
      .baseUrl("https://api.github.com/")
      .build()
  }

  single { get<Retrofit>().create<UserApi>() }
  single { UserApiInteractor(get()) }
}

private const val NETWORK_TIMEOUT_DEFAULT_SECONDS = 90L
