package dev.vinigouveia.factsnorris.shared.service

import retrofit2.http.GET

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
interface FactsService {

    @GET("/jokes/categories")
    fun fetchCategories()

    @GET("/jokes/categories")
    fun fetchFacts()
}