package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId

@Composable
fun CheckoutDestination(
    orderId: OrderId,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Checkout",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Order ID: ${orderId.value}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.Start) {
                Button(onClick = onBack) { Text("Back") }
            }
        }
    }
}
