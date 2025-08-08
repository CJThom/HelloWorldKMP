package com.gpcasiapac.gpchelloworldkmp.presentation.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gpcasiapac.gpchelloworldkmp.common.presentation.theme.GPCTheme
import com.gpcasiapac.gpchelloworldkmp.presentation.cart.CartScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Orders",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(5) { index ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Order #${1000 + index}",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Status: ${if (index % 2 == 0) "Pending" else "In Transit"}",
                        color = if (index % 2 == 0) Color(0xFFFF9800) else Color(0xFF4CAF50)
                    )
                    Text(
                        text = "Items: ${index + 2} items",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CartScreenPreview() {
    GPCTheme {
        Surface {
            OrdersScreen()
        }

    }
}