package com.example.mad3.model

import android.content.Context
import android.widget.Toast
import com.example.mad3.model.validation.validationResult

class FormData (
    private var name:String,
    private var discription:String,

){

    fun validatename():validationResult {

        return if (name.isEmpty()) {
            validationResult.Empty("Disaster name is empty.....information not added")
        }  else {
            validationResult.Valid
        }
    }
    fun validatediscription():validationResult {

        return if (discription.isEmpty()) {
            validationResult.Empty("Disaster description is empty.....information not added")
        }  else {
            validationResult.Valid
        }
    }


    //if toast validation
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}