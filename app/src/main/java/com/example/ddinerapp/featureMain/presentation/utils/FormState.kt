package com.example.ddinerapp.featureMain.presentation.utils

class FormState {
    var fields: List<FormField> = listOf()
        set(value) {
            field = value
        }

    fun validate(): Boolean {
        var valid = true
        for (field in fields) if (!field.validate()) {
            valid = false
            break
        }
        return valid
    }

    fun getData(): Map<String, String> = fields.associate { it.name to it.text }

}