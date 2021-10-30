package dev.vinigouveia.factsnorris.shared.service

import dev.vinigouveia.factsnorris.shared.classes.response.FactsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
interface FactsService {

    @GET("/jokes/search")
    suspend fun fetchFacts(@Query("query") search: String): FactsListResponse
}
