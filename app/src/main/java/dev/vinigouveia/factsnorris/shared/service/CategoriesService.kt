package dev.vinigouveia.factsnorris.shared.service

import retrofit2.http.GET

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface CategoriesService {

    @GET("/jokes/categories")
    suspend fun fetchCategories(): List<String>
}
