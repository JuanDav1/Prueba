package com.example.prueba.dagger

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.prueba.api.CallsAPi
import com.example.prueba.db.AplicationDB
import com.example.prueba.db.DataBaseDao
import com.example.prueba.repositories.LocalRepository
import com.example.prueba.repositories.RemoteRepository
import com.example.prueba.viewmodels.ViewModelFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class UtilsModule {

    private val baseApiUrl = "https://images-api.nasa.gov/"


    @Volatile
    private var INSTANCE: AplicationDB? = null

    @Provides
    @Singleton
    fun getInstanceDB(context: Context): AplicationDB {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AplicationDB::class.java,
                    "prueba_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory ())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    fun getApiCall(retrofit: Retrofit): CallsAPi {
        return retrofit.create(CallsAPi::class.java)
    }

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .build()
            chain.proceed(request)
        }
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getViewModelFactory(remoteRepository: RemoteRepository, localReposiroty: LocalRepository): ViewModelProvider.Factory {
        return ViewModelFactory(remoteRepository, localReposiroty)
    }



    @Provides
    @Singleton
    fun getLocalRepository(dataBaseDao: DataBaseDao): LocalRepository {
        return LocalRepository(dataBaseDao)
    }

    @Provides
    @Singleton
    fun getRemoteRepository(apiCall: CallsAPi): RemoteRepository {
        return RemoteRepository(apiCall)
    }
    @Singleton
    @Provides
    fun provideDao(aplicationDB: AplicationDB): DataBaseDao {
        return aplicationDB.dataBaseDao()
    }
}