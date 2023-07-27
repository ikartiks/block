package com.example.block

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.block.theme.appTypography

@Composable
fun AppText(
    text: String? = null,
    @StringRes textRes: Int? = null,
    textStyle: TextStyle = appTypography.bodyMedium,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    return Text(
        text = text
            ?: textRes?.let { stringResource(id = it) }
            ?: "",
        style = textStyle,
        textAlign = textAlign,
        modifier = modifier
    )
}