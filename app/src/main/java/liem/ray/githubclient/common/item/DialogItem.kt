package liem.ray.githubclient.common.item

import androidx.annotation.StringRes
import liem.ray.githubclient.R

data class DialogItem(
    val message: String,
    @StringRes val dismissTextId: Int = R.string.common_ok,
    val onDismiss: () -> Unit,
)