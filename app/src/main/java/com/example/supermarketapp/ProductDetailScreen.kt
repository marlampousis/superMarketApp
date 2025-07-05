package com.example.supermarketapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supermarketapp.dataentities.Product
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun ProductDetailScreen(productJson: String?, navController: NavController) {
    val decodedJson = URLDecoder.decode(productJson, "UTF-8")
    val product = Gson().fromJson(decodedJson, Product::class.java)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        product.imagePath?.let {
            Image(
                painter = painterResource(id = getImageResId(it)),
                contentDescription = product.name,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
        }

        Text(text = product.name ?: "", style = MaterialTheme.typography.headlineMedium)
        Text(text = product.description ?: "", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Category: ${product.category}")
        Text(text = "Price: ${product.price} € / ${product.unit}")
        Text(text = "Available: ${product.quantity}")

        Spacer(modifier = Modifier.weight(1f)) // Push buttons to bottom

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { /* Add to cart logic */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_to_cart),
                    contentDescription = "Add to Cart",
                    modifier = Modifier.size(40.dp)
                )
            }

            IconButton(onClick = { /* Add to favourites logic */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favourite),
                    contentDescription = "Add to Favourites",
                    modifier = Modifier.size(40.dp)
                )
            }

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

