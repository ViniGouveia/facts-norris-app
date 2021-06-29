package dev.vinigouveia.factsnorris.shared.data

import androidx.annotation.DimenRes

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
data class FactDisplay(
    val id: String,
    val category: String?,
    val description: String,
    @DimenRes val fontSize: Int
)
