package com.example.candradinatha.committee.model

import com.google.gson.annotations.SerializedName

data class SieResponse(
        @field:SerializedName("sie_kegiatan")
        val sieKegiatan: List<Sie>
)