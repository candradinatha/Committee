package com.example.candradinatha.committee.model
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("admin")
	val admin: Int? = null,

	@field:SerializedName("status")
	val status: Boolean = false
)