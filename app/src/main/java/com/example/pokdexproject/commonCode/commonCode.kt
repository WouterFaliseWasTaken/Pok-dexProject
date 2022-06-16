package com.example.pokdexproject.commonCode

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.math.max

fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}

fun <T : ViewModel, A, B> doubleArgsViewModelFactory(constructor: (A, B) -> T):
            (A, B) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A, arg2: B ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg, arg2) as V
            }
        }
    }
}

val Number.dpToPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Number.spToPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt();


object Stopwatch1 {
    const val TAG = "Stopwatch:Basics"

    private var start: Long? = null
    private var timeList = mutableListOf<Long>()

    fun begin() {
        start = System.currentTimeMillis()
    }

    fun startRun() {
        start = System.currentTimeMillis()
    }

    fun stopRun(message: String = "") {
        start ?: return
        timeList.add(System.currentTimeMillis() - start!!)
        abortRun()
    }

    fun abortRun() {
        start = null
    }

    fun reset() {
        start = null
    }

    fun report() {
        if(timeList.isNotEmpty()){
            Log.d(TAG, "----Run complete!----")
            Log.d(TAG, "${timeList.size} runs completed")
            Log.d(TAG, "The average time was ${timeList.average()}")
            Log.d(TAG, "The longest time was ${timeList.maxOrNull()}")
            Log.d(TAG, "The shortest time was ${timeList.minOrNull()}")
            Log.d(TAG, "---------------------")
        }
    }
}


object Stopwatch2 {
    const val TAG = "Stopwatch:Details"

    private var start: Long? = null
    private var timeList = mutableListOf<Long>()

    fun begin() {
        start = System.currentTimeMillis()
    }

    fun startRun() {
        start = System.currentTimeMillis()
    }

    fun stopRun(message: String = "") {
        start ?: return
        timeList.add(System.currentTimeMillis() - start!!)
        abortRun()
    }

    fun abortRun() {
        start = null
    }

    fun reset() {
        start = null
    }

    fun report() {
        if(timeList.isNotEmpty()){
            Log.d(TAG, "----Run complete!----")
            Log.d(TAG, "${timeList.size} runs completed")
            Log.d(TAG, "The average time was ${timeList.average()}")
            Log.d(TAG, "The longest time was ${timeList.maxOrNull()}")
            Log.d(TAG, "The shortest time was ${timeList.minOrNull()}")
            Log.d(TAG, "---------------------")
        }
    }
}


object Stopwatch3 {
    const val TAG = "Stopwatch:Species"

    private var start: Long? = null
    private var timeList = mutableListOf<Long>()

    fun begin() {
        start = System.currentTimeMillis()
    }

    fun startRun() {
        start = System.currentTimeMillis()
    }

    fun stopRun() {
        start ?: return
        timeList.add(System.currentTimeMillis() - start!!)
        abortRun()
    }

    fun abortRun() {
        start = null
    }

    fun reset() {
        start = null
    }

    fun report() {
        if(timeList.isNotEmpty()){
            Log.d(TAG, "----Run complete!----")
            Log.d(TAG, "${timeList.size} runs completed")
            Log.d(TAG, "The average time was ${timeList.average()}")
            Log.d(TAG, "The longest time was ${timeList.maxOrNull()}")
            Log.d(TAG, "The shortest time was ${timeList.minOrNull()}")
            Log.d(TAG, "---------------------")
        }
    }
}
