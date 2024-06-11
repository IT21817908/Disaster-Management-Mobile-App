package com.example.mad3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad3.database.Disaster

class MainActivityData : ViewModel(){
    private val _data= MutableLiveData<List<Disaster>>()


    val data: LiveData<List<Disaster>> =_data


    fun setData(data:List<Disaster>){
        _data.value=data
    }

}