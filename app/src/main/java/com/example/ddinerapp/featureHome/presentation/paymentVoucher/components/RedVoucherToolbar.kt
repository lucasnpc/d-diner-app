package com.example.ddinerapp.featureHome.presentation.paymentVoucher.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ddinerapp.R

@Composable
fun RedVoucherToolbar(closeClick: () -> Unit, shareClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { closeClick() }) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close Voucher Button",
                tint = Color.White
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            tint = Color.Unspecified,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
        )
        IconButton(onClick = { shareClick() }) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share Voucher",
                tint = Color.White
            )
        }
    }
}