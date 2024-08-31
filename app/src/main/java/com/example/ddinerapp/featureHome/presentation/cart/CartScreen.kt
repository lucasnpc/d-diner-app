package com.example.ddinerapp.featureHome.presentation.cart

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ddinerapp.R
import com.example.ddinerapp.common.util.LoadingScreen
import com.example.ddinerapp.common.util.currencyFormat
import com.example.ddinerapp.common.util.quantityFormat
import com.example.ddinerapp.common.util.toShowDateFormat
import com.example.ddinerapp.featureHome.domain.model.MenuItem
import com.example.ddinerapp.featureHome.presentation.cart.components.CartCard
import com.example.ddinerapp.featureHome.presentation.cart.components.MoneyField
import com.example.ddinerapp.featureHome.presentation.cart.util.radioOptions
import com.example.ddinerapp.featureHome.presentation.menuItems.MenuItemViewModel
import com.example.ddinerapp.featureHome.presentation.placedOrders.PlacedOrdersViewModel
import com.example.ddinerapp.featureHome.presentation.util.HomeScreen
import com.example.ddinerapp.featureStartOrder.presentation.orderingDesks.DesksViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val menuItemViewModel: MenuItemViewModel = hiltViewModel()
    val placedOrdersViewModel: PlacedOrdersViewModel = hiltViewModel()
    val desksViewModel: DesksViewModel = hiltViewModel()
    val orderedItems = cartViewModel.orderedItems
    val itemsList = menuItemViewModel.items
    val placedMenuItems = mutableListOf<Pair<MenuItem, Double>>()
    var total = 0.0
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    val (selectedOption, onOptionSelect) = remember {
        mutableStateOf(radioOptions[0])
    }
    var changeValue by remember {
        mutableStateOf(0.0)
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        finishOrder(
            placedMenuItems = placedMenuItems,
            placedOrdersViewModel = placedOrdersViewModel,
            desksViewModel = desksViewModel,
            cartViewModel = cartViewModel,
            selectedOption = selectedOption,
            total = total,
            navController = navController,
            context = context,
            hasStoragePermission = (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true && permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true)
        )
    }

    orderedItems.forEach { orderedItem ->
        orderedItem.placedItems.forEach { placedItem ->
            placedMenuItems.find { item -> item.first.id == placedItem.key }?.let { find ->
                val index = placedMenuItems.indexOf(find)
                placedMenuItems[index] = find.first to find.second + placedItem.value
            } ?: itemsList.find { item -> item.id == placedItem.key }?.let { find ->
                placedMenuItems.add(find to placedItem.value)
            }
        }
    }

    placedMenuItems.forEach {
        total += (it.first.price * it.second)
    }

    when {
        cartViewModel.loading.value || menuItemViewModel.loading.value || placedOrdersViewModel.loading.value || desksViewModel.loading.value -> {
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
                    MoneyField(total = total) { change ->
                        changeValue = change
                    }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            finishOrder(
                                placedMenuItems = placedMenuItems,
                                placedOrdersViewModel = placedOrdersViewModel,
                                desksViewModel = desksViewModel,
                                cartViewModel = cartViewModel,
                                selectedOption = selectedOption,
                                total = total,
                                navController = navController,
                                context = context,
                                hasStoragePermission = true
                            )
                            return@Button
                        }
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        )
                    },
                    enabled = changeValue >= total || selectedOption != "Dinheiro",
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

private fun finishOrder(
    placedMenuItems: MutableList<Pair<MenuItem, Double>>,
    placedOrdersViewModel: PlacedOrdersViewModel,
    desksViewModel: DesksViewModel,
    cartViewModel: CartViewModel,
    selectedOption: String,
    total: Double,
    navController: NavHostController,
    context: Context,
    hasStoragePermission: Boolean,
) {
    val time = System.currentTimeMillis()
    val cleanedItems = mutableMapOf<String, Double>()
    cleanedItems.putAll(placedMenuItems.map {
        it.first.description to it.second
    })
    if (hasStoragePermission)
        createPdfDocument(context, time, cleanedItems, total, selectedOption, placedOrdersViewModel)

    placedOrdersViewModel.concludeOrder(time)
    desksViewModel.disoccupyDesk()
    cartViewModel.registerGain(selectedOption, total)
    navController.navigate(HomeScreen.PaymentVoucherScreen.route + "/${time}/$selectedOption/${total.toFloat()}/$cleanedItems")
}


private fun createPdfDocument(
    context: Context,
    time: Long,
    cleanedItems: MutableMap<String, Double>,
    total: Double,
    paymentWay: String,
    placedOrdersViewModel: PlacedOrdersViewModel
) {
    val pageHeight = 1120
    val pagewidth = 792
    val doc = PdfDocument()
    val title = Paint().apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textSize = 16F
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }
    val text = Paint().apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textSize = 16F
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }
    val mypageInfo = PageInfo.Builder(pagewidth, pageHeight, 1).create()
    val myPage: PdfDocument.Page = doc.startPage(mypageInfo)
    val canvas = myPage.canvas

    canvas.apply {
        drawText(context.getString(R.string.payment_voucher), 396F, 80F, title)
        drawText(time.toShowDateFormat(), 396F, 120F, text)
        drawText("PEDIDO: ", 396F, 160F, title)
        var height = 160F
        cleanedItems.forEach { item ->
            height += 20f
            this.drawText(
                "Item: ${item.key} Qtd. ${item.value.quantityFormat()}",
                396F,
                height,
                text
            )
        }
        height += 40
        drawText("VALOR: ${total.currencyFormat()}", 396F, height, title)
        drawText("PAGAMENTO: $paymentWay", 396F, height + 20, title)
    }

    doc.finishPage(myPage)

    placedOrdersViewModel.writeDoc(
        doc, context
    )
}

