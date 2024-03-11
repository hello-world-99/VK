package com.android.clicker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.vk.ui.screen.catalogue.CatalogueScreen

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "catalogue screen") {
        composable("catalogue screen") {
            CatalogueScreen(navController = navController)
        }
        composable("settings screen") {
            //SettingsScreen(navController = navController)
        }

    }
}


