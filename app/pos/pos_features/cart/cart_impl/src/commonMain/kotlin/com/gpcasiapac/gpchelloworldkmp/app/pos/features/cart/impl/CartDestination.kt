package com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.impl

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartDestination(
    modifier: Modifier = Modifier,
    onCheckout: (com.gpcasiapac.gpchelloworldkmp.app.pos.features.cart.api.OrderId) -> Unit = {}
) {
    val vm: CartViewModel = org.koin.compose.viewmodel.koinViewModel()
    val state = vm.viewState.collectAsState().value

    // Effects: observe one-off navigation and messages
    androidx.compose.runtime.LaunchedEffect(vm.effect) {
        vm.effect.collect { effect ->
            when (effect) {
                is CartScreenContract.Effect.ShowError -> { /* TODO: snackbar if needed */ }
                is CartScreenContract.Effect.ShowToast -> { /* TODO: snackbar if needed */ }
                is CartScreenContract.Effect.Navigation.Checkout -> onCheckout(effect.orderId)
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val cartItems = state.cart?.cartItemList.orEmpty()
            items(cartItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = item.description,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Item ID: ${item.id}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$" + item.price.toString(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = { vm.setEvent(CartScreenContract.Event.RemoveItem(item.id)) }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }
        }

        // Actions
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.setEvent(CartScreenContract.Event.AddSampleItem) }, modifier = Modifier.weight(1f)) {
                Text("Add Random Item")
            }
            Button(
                onClick = { vm.setEvent(CartScreenContract.Event.CheckoutClicked) },
                modifier = Modifier.weight(1f),
                enabled = !state.isLoading && (state.cart?.cartItemList?.isNotEmpty() == true)
            ) {
                Text("Checkout")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Totals
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", fontWeight = FontWeight.Bold)
                    Text("$" + (state.cart?.total ?: 0.0).toString(), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}