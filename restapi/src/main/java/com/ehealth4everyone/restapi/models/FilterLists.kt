package com.ehealth4everyone.restapi.models

import com.google.gson.annotations.SerializedName

class FilterLists {
    private val id = 0

    @SerializedName("start_year")
    var startYear = 0

    @SerializedName("end_year")
    var endYear = 0

    var gender: String? = null
    var countries: ArrayList<String>? = null
    var colors: ArrayList<String>? = null
}