package com.mcvz.besinkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity //SQLite a kaydedilmek için hazır hale geldi
data class Besin (
    @ColumnInfo(name = "isim")  //Databasedeki column infoların adlarını belirledik json dosyamızdakiyle aynı olmak zorunda
    @SerializedName("isim") //Verileri aldığımız apide besinIsım'e karşılık gelen veri neyse serializedName ile onu belirtiyoruz
    val besinIsim:String?,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")   //Bu isimler Apide yazılanla birebir aynı olmak zorunda
    val besinKalori:String?,
    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val besinKarbonhidrat:String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val besinProtein:String?,
    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val besinYag:String?,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val besinGorsel:String?
    ){
    @PrimaryKey(autoGenerate = true)    //otomatik primary key oluşturup uuid içine atacak
    var uuid:Int=0
}