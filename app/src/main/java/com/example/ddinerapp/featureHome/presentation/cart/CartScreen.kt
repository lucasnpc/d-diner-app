package com.example.ddinerapp.featureHome.presentation.cart

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.presentation.menuItems.MenuItemViewModel
import com.example.ddinerapp.featureHome.presentation.orders.OrdersViewModel
import com.example.ddinerapp.featureMain.presentation.orderingDesks.DesksViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen() {
    val cartViewModel: CartViewModel = hiltViewModel()
    val menuItemViewModel: MenuItemViewModel = hiltViewModel()
    val ordersViewModel: OrdersViewModel = hiltViewModel()
    val desksViewModel: DesksViewModel = hiltViewModel()
    val orderedItems = cartViewModel.orderedItems
    val itemsList = menuItemViewModel.items
    val placedMenuItems = mutableListOf<Pair<MenuItem, Double>>()
    val context = (LocalContext.current as? Activity)

    var total = 0.0

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    val radioOptions = listOf("Crédito", "Débito", "Dinheiro", "Pix")
    val (selectedOption, onOptionSelect) = remember {
        mutableStateOf(radioOptions[0])
    }

    orderedItems.forEach { orderedItem ->
        orderedItem.placedItems.forEach { placedItem ->
            itemsList.find { item -> item.id == placedItem.key }?.let { find ->
                placedMenuItems.add(find to placedItem.value)
            }
        }
    }

    placedMenuItems.forEach {
        total += (it.first.price * it.second)
    }
    total.roundToInt()

    when {
        cartViewModel.loading.value || menuItemViewModel.loading.value || ordersViewModel.loading.value || desksViewModel.loading.value -> {
            LoadingScreen()
        }
        else -> {
            BottomDrawer(drawerState = drawerState, drawerContent = {
                radioOptions.forEach {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (it == selectedOption),
                                onClick = {
                                    onOptionSelect(it)
                                },
                                role = Role.RadioButton
                            ),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = it == selectedOption,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colors.onBackground,
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                        Text(
                            text = it,
                            modifier = Modifier
                                .align(CenterVertically)
                                .width(70.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
                if (selectedOption == "Dinheiro")
                    MoneyField(total = total)
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {
                        ordersViewModel.concludeOrder()
                        desksViewModel.disoccupyDesk()
                        context?.finish()
                    },
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    Text(text = "Efetuar pagamento")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }) {
                CartCard(placedMenuItems.toList(), total) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        }
    }
}

@Composable
private fun CartCard(list: List<Pair<MenuItem, Double>>, total: Double, changeState: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(8.dp))
    ) {
        Text(
            text = "Carrinho",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .padding(top = 16.dp, bottom = 16.dp),
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(elevation = 8.dp) {
            Column(modifier = Modifier.padding(PaddingValues(8.dp))) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                    )
                    Text(
                        text = "Pedido em Aberto",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "Items: ${list.sumOf { it.second }.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Total: R$ ${total.toString().replace(".", ",")}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Info Icon",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
        Button(
            onClick = { changeState() },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    3.dp,
                    MaterialTheme.colors.primary,
                    RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        ) {
            Text(text = "Pagar", textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}

@Composable
private fun MoneyField(total: Double) {
    var cashChange by remember { mutableStateOf("") }
    val sum = if (cashChange.isNotEmpty()) cashChange.replace(",", ".").toDouble() - total else 0

    Spacer(modifier = Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = cashChange,
            onValueChange = {
                cashChange = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.width(130.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Troco R$ ${sum.toString().replace(".", ",")}",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(CenterVertically)
        )
    }

}
