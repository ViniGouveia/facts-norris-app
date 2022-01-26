package dev.vinigouveia.factsnorris.shared.classes.response

import com.squareup.moshi.Json

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
data class FactsListResponse(
    @Json(name = "result") val factsList: List<FactResponse>
)
