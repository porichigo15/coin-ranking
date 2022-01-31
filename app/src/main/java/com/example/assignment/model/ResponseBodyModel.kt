package com.example.assignment.model

class ResponseBodyModel<T> {
    var data: T? = null
    var status: String? = null
    var message: String? = null
}