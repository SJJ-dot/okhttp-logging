package com.sjianjun.retrofit.html

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sjianjun.retrofit.html.interceptor.HttpLoggingInterceptor
import com.sjianjun.scheduler.CoroutineScheduler
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import sjj.alog.Log
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test = Retrofit.Builder()
            .baseUrl("http://www.xbiquge.la")
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor {
                        Log.e(1, it)
                    }.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(CoroutineScheduler.IO))
            .build()

            .create(Test::class.java)
        val html = test.html()
        html.subscribe({
            Log.e(it)
        }, {
            Log.e("error", it)
        })
    }

    interface Test {
        @GET(value = "https://www.ddxs.cc/ddxs/773/448784.html")
        fun html(): Observable<String>
    }
}
