package com.example.ddinerapp.featureHome.presentation.paymentVoucher.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddinerapp.R

@Composable
fun SeeVoucherBt(seeVoucherClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { seeVoucherClick() },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = "See Voucher Button",
                    tint = MaterialTheme.colors.primary, modifier = Modifier
                        .height(32.dp)
                        .width(32.dp)
                )
                Text(
                    text = stringResource(R.string.see_voucher),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 16.sp
                )
            }
        }
    }
}