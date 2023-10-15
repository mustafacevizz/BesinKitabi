package com.mcvz.besinkitabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcvz.besinkitabi.model.Besin

class BesinDetayiViewModel:ViewModel() {
    val besinLiveData=MutableLiveData<Besin>()

    fun roomVerisiAl(){
        val muz = Besin("Muz","100","10","5","1","www.test.com")
        besinLiveData.value=muz
    }
}