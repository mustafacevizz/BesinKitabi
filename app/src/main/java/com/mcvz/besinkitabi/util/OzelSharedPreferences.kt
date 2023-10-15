package com.mcvz.besinkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import kotlinx.coroutines.internal.synchronized

class OzelSharedPreferences {

    companion object{
        private val ZAMAN="zaman"
        private var sharedPreferences:SharedPreferences?=null

        @Volatile private var instance:OzelSharedPreferences?=null

        private val lock=Any()
        operator fun invoke(context:Context):OzelSharedPreferences= instance?: kotlin.synchronized(lock){
            instance?: ozelSharedPreferencesYap(context).also {
                instance=it
            }

        }

        private fun ozelSharedPreferencesYap(context:Context):OzelSharedPreferences{
            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }


    }
    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit(commit=true){
            putLong(ZAMAN,zaman)
        }
    }
    fun zamaniAl()=sharedPreferences?.getLong(ZAMAN,0)


}