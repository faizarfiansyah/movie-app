package com.example.core.di

import androidx.room.Room
import com.example.core.data.repository.IMovieRepository
import com.example.core.data.repository.MovieRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.MovieDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.api.API
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("movieapp".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val httpBuilder = OkHttpClient.Builder()
        val certificatePinner = CertificatePinner.Builder()
            .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .build()
        val client1 = httpBuilder
            .certificatePinner(certificatePinner)
            .build()
        val baseUrl = "https://api.themoviedb.org/3/"
        val retrofit: Retrofit = Retrofit.Builder()
            .client(client1)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(API::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }
}
