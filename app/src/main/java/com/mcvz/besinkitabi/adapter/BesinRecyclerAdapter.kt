package com.mcvz.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mcvz.besinkitabi.R
import com.mcvz.besinkitabi.model.Besin
import com.mcvz.besinkitabi.util.gorselIndir
import com.mcvz.besinkitabi.util.placeHolderYap
import com.mcvz.besinkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi:ArrayList<Besin>):RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {
    class BesinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.besin_recycler_row,parent,false)
        return BesinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.isim).text=besinListesi[position].besinIsim
        holder.itemView.findViewById<TextView>(R.id.kalori).text=besinListesi[position].besinKalori

        holder.itemView.setOnClickListener{
            val action=BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(0)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.findViewById<ImageView>(R.id.imageView).gorselIndir(besinListesi.get(position).besinGorsel,
            placeHolderYap(holder.itemView.context)
        )

    }
    fun besinListesiniGuncelle(yeniBesinListesi:List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }
}