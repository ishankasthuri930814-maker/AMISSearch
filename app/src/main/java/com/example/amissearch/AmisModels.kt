package com.example.amissearch

import com.google.gson.annotations.SerializedName

// Request Data Model
data class AmisRequest(
    @SerializedName("query") val query: String,
    @SerializedName("type") val type: String // "nic" athava "name"
)

// Response Data Model
data class AmisResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: List<SuspectRecord>?
)

data class SuspectRecord(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("nic") val nic: String?,
    @SerializedName("offense") val offense: String?,
    @SerializedName("date") val date: String?
)
