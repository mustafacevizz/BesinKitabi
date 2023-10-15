package com.mcvz.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.mcvz.besinkitabi.R
import com.mcvz.besinkitabi.viewmodel.BesinDetayiViewModel


class BesinDetayiFragment : Fragment() {
    private lateinit var viewModel: BesinDetayiViewModel
    var besinIsim: TextView?=null
    var besinKalori:TextView?=null
    var besinKarbonhidrat:TextView?=null
    var besinProtein:TextView?=null
    var besinYag:TextView?=null
    private var besinId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_besin_detayi, container, false)
        besinIsim=view.findViewById(R.id.besinIsim)
        besinKalori=view.findViewById(R.id.besinKalori)
        besinKarbonhidrat=view.findViewById(R.id.besinKarbonhidrat)
        besinProtein=view.findViewById(R.id.besinProtein)
        besinYag=view.findViewById(R.id.besinYag)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiAl()

        arguments?.let {
            besinId=BesinDetayiFragmentArgs.fromBundle(it).besinId
            println(besinId)
        }
        observeLiveData()


    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin->
            besin?.let {
                besinIsim?.text=it.besinIsim
                besinKalori?.text=it.besinKalori
                besinKarbonhidrat?.text=it.besinKarbonhidrat
                besinProtein?.text=it.besinProtein
                besinYag?.text=it.besinYag
            }
        })
    }
}