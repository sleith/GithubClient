package liem.ray.githubclient.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import liem.ray.githubclient.common.item.DialogItem

@Composable
fun showDialog(item: DialogItem) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = item.onDismiss) {
                Text(text = stringResource(id = item.dismissTextId))
            }
        },
        text = { Text(text = item.message) }
    )
}