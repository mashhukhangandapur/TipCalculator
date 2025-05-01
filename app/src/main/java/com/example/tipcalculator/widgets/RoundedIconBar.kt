package com.example.tipcalculator.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun RoundedIconBar(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color  = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onClick: () -> Unit,
    elevation: Dp = 4.dp
) {
    val iconButtonSizeModifier = modifier.size(40.dp)

    Surface(
        modifier = modifier
            .padding(all = 4.dp)
            .clickable { onClick.invoke() }
            .then(iconButtonSizeModifier),
        shape = CircleShape,
        color = backgroundColor,
        tonalElevation = elevation
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Plus or minus icon",
            tint = tint,
            modifier = Modifier.padding(8.dp)
        )
    }
}
