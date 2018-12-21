package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class Notification(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("id_pemberitahuan")
	val idPemberitahuan: String? = null,

	@field:SerializedName("id_mahasiswa")
	val idMahasiswa: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null
)