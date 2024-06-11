package com.example.mad3.database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Disaster(

var name:String?,
var description:String?,
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
