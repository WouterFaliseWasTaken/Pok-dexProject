package com.example.pokdexproject.network

import android.util.Log
import com.example.pokdexproject.commonCode.Stopwatch1
import com.example.pokdexproject.commonCode.Stopwatch2
import com.example.pokdexproject.commonCode.Stopwatch3
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
        val response: okhttp3.Response = chain.proceed(request)
        Stopwatch3.startRun()
        return response
    }
}

internal class LoggingInterceptorB : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = chain.request()
        val response: okhttp3.Response = chain.proceed(request)
        Stopwatch2.startRun()
        return response
    }
}

internal class LoggingInterceptorA : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = chain.request()
        val response: okhttp3.Response = chain.proceed(request)
        Stopwatch1.startRun()
        return response
    }
}