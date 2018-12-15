package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("status_active")
	val statusActive: String? = null,

	@field:SerializedName("nama_mahasiswa")
	val namaMahasiswa: String? = null,

	@field:SerializedName("angkatan")
	val angkatan: String? = null,

	@field:SerializedName("id_mahasiswa")
	val idMahasiswa: String? = null,

	@field:SerializedName("login_token")
	val loginToken: String? = null,

	@field:SerializedName("foto_mahasiswa")
	val fotoMahasiswa: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)