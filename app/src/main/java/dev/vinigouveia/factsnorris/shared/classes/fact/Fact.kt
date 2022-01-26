package dev.vinigouveia.factsnorris.shared.classes.fact

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

data class Fact(
    val id: String,
    val category: String?,
    val url: String,
    val description: String
)
