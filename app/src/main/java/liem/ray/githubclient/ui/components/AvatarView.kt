package liem.ray.githubclient.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import liem.ray.githubclient.R

@Composable
fun AvatarView(avatarUrl: String?, size: Dp) {
    GlideImage(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        imageModel = { avatarUrl },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ),
        component = rememberImageComponent {
            +PlaceholderPlugin.Failure(painterResource(id = R.drawable.ic_avatar))
        },
        previewPlaceholder = painterResource(id = R.drawable.ic_avatar),
    )
}

@Composable
@Preview(showBackground = true)
private fun PreviewAvatarView() {
    AvatarView(avatarUrl = "https://avatars.githubusercontent.com/u/7?v=4", size = 100.dp)
}