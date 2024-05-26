package com.example.vamzsem



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vamzsem.food_database.Food

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun FoodList(foodViewModel: FoodViewModel) {
    val foodList by foodViewModel.foodList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Headers for the food list columns
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = "Type",
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Calories",
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f),

                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(35.dp)) // Spacer for the delete button
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(foodList) { food ->
                FoodItem(food, onDeleteClick = { foodViewModel.deleteFood(food) })
            }
        }
    }
}

@Composable
fun FoodItem(food: Food, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = food.nameOfFood,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = food.typeOfFood,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = food.calories.toString(),
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.End
        )
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Food"
            )
        }
    }
}
