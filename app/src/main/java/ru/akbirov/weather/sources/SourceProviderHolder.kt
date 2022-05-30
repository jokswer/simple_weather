package ru.akbirov.weather.sources

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.akbirov.weather.BuildConfig
import ru.akbirov.weather.app.Constants
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.SourcesProvider
import ru.akbirov.weather.app.model.appSettings.AppSettings
import ru.akbirov.weather.sources.base.RetrofitConfig
import ru.akbirov.weather.sources.base.RetrofitSourcesProvider

object SourceProviderHolder {

    val sourcesProvider: SourcesProvider by lazy {
        val gson = Gson().newBuilder().create()

        val weatherConfig = RetrofitConfig(
            retrofit = createRetrofit(
                baseUrl = Constants.WEATHER_URL,
                client = createWeatherOkHttpClient(),
                gson = gson
            ),
            gson = gson
        )

        val geocodeConfig = RetrofitConfig(
            retrofit = createRetrofit(
                baseUrl = Constants.GEO_URL,
                client = createGeocodeOkHttpClient(),
                gson = gson
            ),
            gson = gson
        )

        RetrofitSourcesProvider(weatherConfig = weatherConfig, geocodeConfig = geocodeConfig)
    }

    private fun createRetrofit(gson: Gson, baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createGeocodeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor())
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createWeatherOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor())
            .addInterceptor(createSystemSettingsInterceptor(Singletons.appSettings))
            .addInterceptor(createCoordinateInterceptor(Singletons.appSettings))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder()

            val newUrl = url.addQueryParameter("appid", BuildConfig.OPENWEATHER_TOKEN).build()

            return@Interceptor chain.proceed(request.newBuilder().url(newUrl).build())
        }
    }

    private fun createSystemSettingsInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder()

            val language = settings.getLanguage()
            val unit = settings.getUnit()

            val newUrl = url.addQueryParameter("lang", language)
                .addQueryParameter("units", unit)
                .build()

            return@Interceptor chain.proceed(request.newBuilder().url(newUrl).build())
        }
    }

    private fun createCoordinateInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder()

            val coordunate = settings.getCoordinate()

            val newUrl = url.addQueryParameter("lat", coordunate?.lat.toString())
                .addQueryParameter("lon", coordunate?.lon.toString())
                .build()

            return@Interceptor chain.proceed(request.newBuilder().url(newUrl).build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}