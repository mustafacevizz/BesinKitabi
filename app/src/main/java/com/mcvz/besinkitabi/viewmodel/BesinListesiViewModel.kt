package com.mcvz.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mcvz.besinkitabi.model.Besin
import com.mcvz.besinkitabi.servis.BesinAPIServis
import com.mcvz.besinkitabi.servis.BesinDatabase
import com.mcvz.besinkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application):BaseViewModel(application) {
    val besinler=MutableLiveData<List<Besin>>()
    val besinHataMesaji=MutableLiveData<Boolean>()
    val besinYukleniyor=MutableLiveData<Boolean>()
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())
    private var guncellemeZamani=10 * 60 * 1000 * 1000 * 1000L

    private val besinAPIServis=BesinAPIServis()
    private val disposable=CompositeDisposable()


    fun refreshData(){
        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani!=null&&kaydedilmeZamani!=0L&&System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            verileriSQLitetanAl()
        }else{
            verileriInternettenAl()
        }




    }
    fun refreshFromInternet(){
        verileriInternettenAl()
    }
    private fun verileriSQLitetanAl(){
        besinYukleniyor.value=true
        launch {
            val besinListesi=BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri Roomdan aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value=true

        disposable.add(besinAPIServis.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<Besin>>(){
                override fun onSuccess(t: List<Besin>) {
                    //başarılı olursa
                    sqliteSakla(t)
                    Toast.makeText(getApplication(),"Besinleri İnternetten aldık",Toast.LENGTH_LONG).show()


                }

                override fun onError(e: Throwable) {
                    besinHataMesaji.value=true
                    besinYukleniyor.value=false
                    e.printStackTrace()
                }

                })
            )
    }
    private fun besinleriGoster(besinlerListesi:List<Besin>){
        besinler.value=besinlerListesi
        besinYukleniyor.value=false
        besinHataMesaji.value=false
    }

    private fun sqliteSakla(besinListesi:List<Besin>){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi=dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i<besinListesi.size){
                besinListesi[i].uuid=uuidListesi[i].toInt()
                i=i+1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }


}