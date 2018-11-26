package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class Committee(

	@field:SerializedName("tgl_rapat_perdana")
	val tglRapatPerdana: String? = null,

	@field:SerializedName("nama_kegiatan")
	val namaKegiatan: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("tgl_kegiatan")
	val tglKegiatan: String? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("foto_kegiatan")
	val fotoKegiatan: String? = null
)