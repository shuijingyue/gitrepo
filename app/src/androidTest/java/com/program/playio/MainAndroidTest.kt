package com.program.playio

import android.util.Log
import android.util.SparseArray
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainAndroidTest {

    companion object {
        private const val TAG = "MainAndroidTest"
    }

    @Test
    fun context_test() {
        Assert.assertEquals(
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext,
            ApplicationProvider.getApplicationContext())
    }

    @Test
    fun sparse_array_test() {
        val array1 = SparseArray<Int>()
        array1.put(1, 1)
        array1.put(2, 2)
        val array2 = SparseArray<Int>()
        array2.put(2, 2)
        array2.put(1, 1)
        Log.d(TAG, "array1 $array1")
        Log.d(TAG, "array2 $array2")
    }
}