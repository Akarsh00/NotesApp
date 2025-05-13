package com.aki.notesapp.feature.popupnote

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aki.notesapp.common.util.iconFromAddNoteOtherOption
import com.aki.notesapp.common.util.listOfPopupAddNotesAction
import com.aki.notesapp.feature.popupnote.state.OtherOptionState
import com.aki.notesapp.ui.theme.DarkRed


@Composable
fun AddOtherOptionPopup(
    modifier: Modifier = Modifier,
    otherOptions: List<OtherOptionState>,
    onAction: (OtherOptionState) -> Unit
) {
    ShowOtherOptions(modifier, otherOptions, onAction)
}

@Composable
fun ShowOtherOptions(
    modifier: Modifier,
    otherOptions: List<OtherOptionState>,
    onAction: (OtherOptionState) -> Unit
) {
    Card(modifier = modifier) {
        LazyRow(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp)
        ) {

            items(otherOptions) { item ->
                val colours = if (item.isSelected) {
                    DarkRed
                } else {
                    Color(0xFF889AAA)
                }
                Box(modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onAction.invoke(item)
                    }) {
                    iconFromAddNoteOtherOption(item.type)?.let { icon ->
                        Image(
                            painter = painterResource(icon),
                            modifier = Modifier
                                .size(size = 24.dp)
                                .align(Alignment.Center),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = colours)
                        )
                        VerticalDivider(
                            color = Color(0xFF889AAA),
                            thickness = 2.dp,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .align(Alignment.TopEnd)
                        )
                    }
                    if (item.isSelected) {
                        HorizontalDivider(
                            color = colours,
                            thickness = 2.dp,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }


        }
    }

}

@Preview
@Composable
fun AddOtherOptionPopupPreview(modifier: Modifier = Modifier) {
    AddOtherOptionPopup(otherOptions = listOfPopupAddNotesAction()) {
    }
}