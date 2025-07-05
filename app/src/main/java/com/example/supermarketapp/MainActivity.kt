package com.example.supermarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supermarketapp.dataentities.AppDatabase
import com.example.supermarketapp.dataentities.ProductRepository
import com.example.supermarketapp.dataentities.ProductViewModel
import com.example.supermarketapp.dataentities.ProductViewModelFactory
import com.example.supermarketapp.ui.theme.SupermarketTheme
import com.google.gson.Gson
import java.net.URLEncoder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SupermarketTheme {

                val context = LocalContext.current
                val db = AppDatabase.getDatabase(context)
                val repository = ProductRepository(db.ProductDao())
                val viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(repository))

                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("product_list") {
                            ProductListScreen(viewModel, navController)
                        }
                        composable("product_detail/{productJson}") { backStackEntry ->
                            val productJson = backStackEntry.arguments?.getString("productJson")
                            ProductDetailScreen(productJson = productJson, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var clicked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.supermarket_logo),
            contentDescription = "Supermarket Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (clicked) "Thank you for visiting!" else "Welcome to the Supermarket!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("product_list") }) {
            Text(text = "View Products")
        }
    }
}

fun getImageResId(imageName: String): Int {
    return when (imageName.lowercase()) {
        "apples" -> R.drawable.apples
        "banana" -> R.drawable.banana
        "mango" -> R.drawable.mango
        "cherries" -> R.drawable.cherries
        "watermelon" -> R.drawable.watermelon
        "blueberries" -> R.drawable.blueberries
        "tomatoes" -> R.drawable.tomatoes
        "potatoes" -> R.drawable.potatoes
        "cucumbers" -> R.drawable.cucumbers
        "lettuce" -> R.drawable.lettuce
        "bread" -> R.drawable.bread
        "wheat_crackers" -> R.drawable.wheat_crackers
        "yogurt" -> R.drawable.yogurt
        "milk" -> R.drawable.milk
        "feta" -> R.drawable.feta
        "gouda" -> R.drawable.gouda
        "beef" -> R.drawable.beef
        "pork" -> R.drawable.pork
        "chicken" -> R.drawable.chicken
        "chicken_nuggets" -> R.drawable.chicken_nuggets
        "cereal" -> R.drawable.cereal
        "coffee" -> R.drawable.coffee
        "marmalade" -> R.drawable.marmalade
        "peas" -> R.drawable.peas
        "ice cream" -> R.drawable.ice_cream
        "spaghetti" -> R.drawable.spaghetti
        "rice" -> R.drawable.rice
        "beans" -> R.drawable.beans
        "lentil" -> R.drawable.lentil
        "chickpeas" -> R.drawable.chickpeas
        "chips" -> R.drawable.chips
        "popcorn" -> R.drawable.popcorn
        "candy" -> R.drawable.candy
        "chocolate" -> R.drawable.chocolate
        "cookies" -> R.drawable.cookies
        "croissant" -> R.drawable.croissant
        "gin" -> R.drawable.gin
        "beer" -> R.drawable.beer
        "soda" -> R.drawable.soda
        "orange_juice" -> R.drawable.orange_juice
        "vodka" -> R.drawable.vodka
        "malibu" -> R.drawable.malibu
        "laundry_detergent" -> R.drawable.laundry_detergent
        "dish_detergent" -> R.drawable.dish_detergent
        "bleach" -> R.drawable.bleach
        "sponge" -> R.drawable.sponge
        "gloves" -> R.drawable.gloves
        "aek" -> R.drawable.aek
        "paok" -> R.drawable.paok
        else -> R.drawable.ic_launcher_foreground
    }
}

@Composable
fun ProductListScreen(viewModel: ProductViewModel, navController: NavHostController) {

    // Αυτό διασφαλίζει ότι όταν ανοίγει η οθόνη, φορτώνονται τα προϊόντα
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Product List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (viewModel.productList.isEmpty()) {
            Text("No products found.")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.productList) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            product.imagePath?.let { imageName ->
                                val imageResId = getImageResId(imageName)
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = product.name,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(4.dp)
                                )
                            }
                            Text(
                                text = product.name ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.clickable {
                                    val productJson = URLEncoder.encode(Gson().toJson(product), "UTF-8")
                                    navController.navigate("product_detail/${productJson}")
                                }
                            )

                            Text(text = "Category: ${product.category}")
                            Text(text = "Price: ${product.price}€ per ${product.unit}")
                            Text(text = "Quantity: ${product.quantity}")
                        }
                    }
                }
            }
        }
    }
}