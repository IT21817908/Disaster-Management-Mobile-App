package com.example.mad3.model.validation

sealed class validationResult{
    data class Empty(val errorMessage:String):validationResult()
    data class Invalid(val errorMessage: String):validationResult()
    object Valid:validationResult()
}
