package edu.ucne.samuelduran_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object ListScreen : Screen() {
        const val route = "list_screen"
    }

    @Serializable
    object RegistroScreen : Screen() {
        const val route = "registro_screen"
    }

    @Serializable
    data class EditAlgoScreen(val algoId: Int) : Screen() {
        val route = "edit_algo_screen/$algoId"
    }

    @Serializable
    data class DeleteAlgoScreen(val algoId: Int) : Screen() {
        val route = "delete_algo_screen/$algoId"
    }
}
