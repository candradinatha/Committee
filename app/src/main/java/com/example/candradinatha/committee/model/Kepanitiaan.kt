package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class Kepanitiaan(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("status_active")
	val statusActive: String? = null,

	@field:SerializedName("nama_mahasiswa")
	val namaMahasiswa: String? = null,

	@field:SerializedName("angkatan")
	val angkatan: String? = null,

	@field:SerializedName("job_desc")
	val jobDesc: String? = null,

	@field:SerializedName("tgl_daftar")
	val tglDaftar: String? = null,

	@field:SerializedName("kuota")
	val kuota: String? = null,

	@field:SerializedName("id_mahasiswa")
	val idMahasiswa: String? = null,

	@field:SerializedName("nama_koor")
	val namaKoor: String? = null,

	@field:SerializedName("id_kegiatan")
	val idKegiatan: String? = null,

	@field:SerializedName("sie")
	val sie: String? = null,

	@field:SerializedName("alasan")
	val alasan: String? = null,

	@field:SerializedName("id_line_koor")
	val idLineKoor: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("id_sie_kegiatan")
	val idSieKegiatan: String? = null,

	@field:SerializedName("id_perangkat")
	val idPerangkat: String? = null,

	@field:SerializedName("login_token")
	val loginToken: String? = null,

	@field:SerializedName("id_kepanitiaan")
	val idKepanitiaan: String? = null,

	@field:SerializedName("status_panitia")
	val statusPanitia: String? = null,

	@field:SerializedName("foto_mahasiswa")
	val fotoMahasiswa: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("tgl_rapat_perdana")
	val tglRapatPerdana: String? = null,

	@field:SerializedName("foto_kegiatan")
	val fotoKegiatan: String? = null,

	@field:SerializedName("nama_kegiatan")
	val namaKegiatan: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("tgl_kegiatan")
	val tglKegiatan: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)