package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class AddSieResponse(

	@field:SerializedName("job_desc")
	val jobDesc: String? = null,

	@field:SerializedName("kuota")
	val kuota: String? = null,

	@field:SerializedName("nama_koor")
	val namaKoor: String? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: String? = null,

	@field:SerializedName("sie")
	val sie: String? = null,

	@field:SerializedName("id_line_koor")
	val idLineKoor: String? = null
)