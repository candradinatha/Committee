package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class JoinResponse(

	@field:SerializedName("id_sie_kegiatan")
	val idSieKegiatan: String? = null,

	@field:SerializedName("tgl_daftar")
	val tglDaftar: String? = null,

	@field:SerializedName("id_mahasiswa")
	val idMahasiswa: String? = null,

	@field:SerializedName("alasan")
	val alasan: String? = null
)