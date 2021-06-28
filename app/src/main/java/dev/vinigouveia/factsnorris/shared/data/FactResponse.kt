package dev.vinigouveia.factsnorris.shared.data

import com.squareup.moshi.Json

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
data class FactResponse(
    @Json(name = "id") val id: String,
    @Json(name = "categories") val categories: List<String>,
    @Json(name = "url") val url: String,
    @Json(name = "value") val description: String
)
