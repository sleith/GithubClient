package liem.ray.githubclient.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import liem.ray.githubclient.R

@Composable
fun IconTextView(
    @DrawableRes iconResId: Int,
    text: String,
    iconTintColor: Color = Color.Black,
    iconSize: Dp,
    textColor: Color = Color.Black,
    contentDescription: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            tint = iconTintColor,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, style = TextStyle(color = textColor))
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewIconTextView() {
    IconTextView(iconResId = R.drawable.ic_email, text = "Text here", iconSize = 12.dp)
}