package com.ciromine.example.producttest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ciromine.example.producttest.ui.productdetail.ProductDetailScreen
import com.ciromine.example.producttest.ui.productdetail.ProductDetailViewModel
import com.ciromine.example.producttest.ui.productlist.ProductListScreen
import com.ciromine.example.producttest.ui.productlist.ProductListViewModel
import com.ciromine.example.producttest.ui.productlist.ProductUiState

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val listViewModel: ProductListViewModel = hiltViewModel()
    val state by listViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "productList",
        modifier = modifier
    ) {
        composable("productList") {
            ProductListScreen(
                state = state,
                viewModel = listViewModel,
                modifier = Modifier,
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }

        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0

            if (state is ProductUiState.Success) {
                val product =
                    (state as ProductUiState.Success).productList.results.find { it.id == productId }

                if (product != null) {
                    val detailViewModel: ProductDetailViewModel = hiltViewModel()

                    ProductDetailScreen(
                        product = product,
                        viewModel = detailViewModel
                    )
                }
            }
        }
    }
}
