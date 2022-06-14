package com.example.pokdexproject.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okio.IOException
import retrofit2.Response
import java.lang.String


/**
 * Created by Ikhiloya Imokhai on 2019-10-19.
 *
 *
 * Retrofit Interceptor to intercept and decrypt response from the server
 */
internal class LoggingInterceptorC : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = chain.request()
        Log.i("Network","Species: Request sent at " + System.currentTimeMillis() + " ms")
        val response: okhttp3.Response = chain.proceed(request)
        Log.i("Network","Species: Response received at " + System.currentTimeMillis() + " ms")
        return response
    }
}

internal class LoggingInterceptorB : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = chain.request()
        Log.i("Network","Details: Request sent at " + System.currentTimeMillis() + " ms")
        val response: okhttp3.Response = chain.proceed(request)
        Log.i("Network","Details: Response received at " + System.currentTimeMillis() + " ms")
        return response
    }
}

internal class LoggingInterceptorA : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = chain.request()
        Log.i("Network","Basics: Request sent at " + System.currentTimeMillis() + " ms")
        val response: okhttp3.Response = chain.proceed(request)
        Log.i("Network","Basics: Response received at " + System.currentTimeMillis() + " ms")
        return response
    }
}