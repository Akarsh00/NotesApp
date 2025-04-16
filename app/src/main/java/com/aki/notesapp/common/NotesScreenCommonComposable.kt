package com.aki.notesapp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AssistChipNoteItem(modifier: Modifier = Modifier, text: String) {
    AssistChip(
        modifier = modifier
            .padding(4.dp),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = Color(0XFF889AAA),
            disabledBorderColor = Color(0XFF889AAA),
            borderWidth = 1.dp
        ),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = Color(
                0XFF889AAA
            )
        ),
        onClick = { },
        label = {
            Text(text = text)
        })
}

@Composable
fun NotesAttachmentImage(modifier: Modifier = Modifier, path: String) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(path.toUri()) // handle content:// uri properly
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(model = imageRequest)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(50.dp)
    )
}

@Composable
fun NotesItemIconImage(modifier: Modifier = Modifier, icon: Int) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(icon),
        contentDescription = null
    )
}



@Composable
fun EmptyNoteViews(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .widthIn(70.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        )
        VerticalDivider(
            modifier = Modifier.height(50.dp),
            color = Color(0xFFFFBEBE)
        )
    }
    HorizontalDivider(color = Color(0xFFCAD1EB))
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HeaderToolbar(
    title: String, arrowBack: ImageVector, onIconClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { onIconClicked() }) {
                Icon(
                    imageVector = arrowBack, contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        modifier = Modifier.shadow(elevation = 4.dp)
    )
}

@Composable
fun AssistChipNoteItem(text: String) {
    AssistChip(
        modifier = Modifier
            .padding(4.dp),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = Color(0XFF889AAA),
            disabledBorderColor = Color(0XFF889AAA),
            borderWidth = 1.dp
        ),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = Color(
                0XFF889AAA
            )
        ),
        onClick = { },
        label = {
            Text(text = text)
        })
}
