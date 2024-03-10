package com.app.capstone.core.di

import androidx.multidex.BuildConfig
import androidx.room.Room
import com.app.capstone.core.data.ProductRepository
import com.app.capstone.core.data.source.local.LocalDataSource
import com.app.capstone.core.data.source.local.room.ProductDatabase
import com.app.capstone.core.data.source.remote.RemoteDataSource
import com.app.capstone.core.data.source.remote.network.ApiService
import com.app.capstone.core.domain.repository.IProductRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val urlBase = "https://fakestoreapi.com/"

val databaseModule = module {
    factory { get<ProductDatabase>().productDao() }
    single {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("password".toCharArray())
        val factory = SupportFactory(passPhrase)
        Room.databaseBuilder(
            androidContext(),
            ProductDatabase::class.java, "Product.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {

    // Logging with level
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    // set up certificate pinner
    val hostName = "fakestoreapi.com"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostName, "sha256/NhyY+Wdb5NLalYvJmG9JbPeZ+6LgXjCLmTILNyHwKIA=")
        .add(hostName, "sha256/81Wf12bcLlFHQAfJluxnzZ6Frg+oJ9PWY/Wrwur8viQ=")
        .add(hostName, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
        .build()

    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IProductRepository> { ProductRepository(get(), get()) }
}