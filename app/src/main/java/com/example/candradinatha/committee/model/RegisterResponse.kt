package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
        @field:SerializedName("status")
        val status: Boolean = false
)