package com.example.candradinatha.committee.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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

) : Parcelable {

	companion object {
	    const val TABLE_COMMITTEE: String = "TABLE_COMMITTEE"
		const val COMMITTEE_RAPAT: String = "COMMITTEE_RAPAT"
		const val COMMITTEE_NAME: String = "COMMITTEE_NAME"
		const val COMMITTEE_DESC: String = "COMMITTEE_DESC"
		const val COMMITTEE_DATE: String = "COMMITTEE_DATE"
		const val COMMITTEE_ID: String = "COMMITTEE_ID"
		const val COMMITTEE_STATUS: String = "COMMITTEE_STATUS"
		const val COMMITTEE_FOTO: String = "COMMITTEE_FOTO"
	}
}