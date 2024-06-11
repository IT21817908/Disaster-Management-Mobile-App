package com.example.mad3


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mad3.database.Disaster
import com.example.mad3.database.DisasterRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisasterAdapter (items:List<Disaster>, repository: DisasterRepository, viewModel: MainActivityData):RecyclerView.Adapter<DisasterViewHolder>(){
    var context: Context? =null
    val items =items
    val repository=repository
    val viewModel=viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_disaster,parent,false)
        context = parent.context
        return DisasterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DisasterViewHolder, position: Int) {
        val disaster = items[position]
        val disasterInfo = "${disaster.name},\n${disaster.description}\n"
        holder.ch.text = disasterInfo
        holder.delete.setOnClickListener{
            val isChecked=holder.ch.isChecked

            if(isChecked){
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(items.get(position))
                    val data= repository.getAllDisaster()
                    withContext(Dispatchers.Main){
                        viewModel.setData((data))
                    }
                }
            }else {
                Toast.makeText(context, "select the Disaster", Toast.LENGTH_LONG).show()
            }
        }
    }
}