package com.mcvz.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mcvz.besinkitabi.R
import com.mcvz.besinkitabi.adapter.BesinRecyclerAdapter
import com.mcvz.besinkitabi.viewmodel.BesinListesiViewModel


class BesinListesiFragment : Fragment() {
    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter=BesinRecyclerAdapter(arrayListOf())
    var besinListRecycler:RecyclerView? = null
    var besinHataMesaji:TextView?=null
    var besinYukleniyor:ProgressBar?=null
    var swipeRefreshLayout:SwipeRefreshLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_besin_listesi, container, false)
        besinListRecycler = view.findViewById(R.id.besinListRecycler)
        besinHataMesaji = view.findViewById(R.id.besinHataMesaji)
        besinYukleniyor = view.findViewById(R.id.besinYukleniyor)
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout)



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        besinListRecycler?.layoutManager=LinearLayoutManager(context)
        besinListRecycler?.adapter=recyclerBesinAdapter

        swipeRefreshLayout?.setOnRefreshListener{
            besinYukleniyor?.visibility=View.VISIBLE
            besinHataMesaji?.visibility=View.GONE
            besinListRecycler?.visibility=View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout?.isRefreshing=false
        }
        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler->
            besinler?.let {
                besinListRecycler?.visibility=View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })
        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if (it){
                    besinHataMesaji?.visibility=View.VISIBLE
                    besinListRecycler?.visibility=View.GONE
                }else
                    besinHataMesaji?.visibility=View.GONE

            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor->
            yukleniyor?.let {
                if (it){
                    besinListRecycler?.visibility=View.GONE
                    besinHataMesaji?.visibility=View.GONE
                    besinYukleniyor?.visibility=View.VISIBLE
                }else
                    besinYukleniyor?.visibility=View.GONE

            }
        })
    }

}