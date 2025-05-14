package com.aki.notesapp.feature.hashtags

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aki.notesapp.feature.add_notes.presentation.state.AddNotesState
import com.aki.notesapp.feature.hashtags.action.HashTagScreenAction
import com.aki.notesapp.feature.hashtags.state.HashTagsModel

@Composable
fun AddMoreOptionBottomSheet(
    state: AddNotesState,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit
) {
    val viewModel: AddHashTagsViewModel = hiltViewModel()
    val hashtagsState by viewModel.addHashTagState.collectAsStateWithLifecycle()
    val showSheet = remember { state.showBottomSheet }
    MoreOptionBottomSheet(
        modifier = Modifier,
        showSheet = showSheet,
        addMoreState = hashtagsState,
        onDismiss = { onDismiss.invoke() },
        onSave = { onSave.invoke(hashtagsState.hashTagList) },
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionBottomSheet(
    modifier: Modifier = Modifier,
    showSheet: Boolean,
    addMoreState: HashTagsModel,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onAction: (HashTagScreenAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier, onDismissRequest = {
                onDismiss.invoke()
            }, sheetState = sheetState
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("Add Hashtags '#'", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Write comma separated hashtags ")
                TextField(
                    modifier = Modifier.focusRequester(focusRequester),

                    value = "" + addMoreState.hashTagText,
                    onValueChange = { newValue -> onAction(HashTagScreenAction.OnTextChange(newValue)) })


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    addMoreState.hashTagList.forEach {
                        AssistChip(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(4.dp),
                            onClick = { },
                            label = {
                                Text(text = it)
                            })
                    }
                }


                BottomSheetBottomBar(
                    addMoreState = addMoreState,
                    onDismiss = { onDismiss.invoke() },
                    onSave = { onSave.invoke(addMoreState.hashTagText) })
            }
        }
    }


}

@Composable
private fun BottomSheetBottomBar(
    addMoreState: HashTagsModel,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = {
                onDismiss.invoke()
            }, modifier = Modifier
                .weight(1f), shape = RoundedCornerShape(8.dp)

        ) {
            Text(
                "Cancel",
                fontWeight = FontWeight.W400,
                color = Color(0xFF2A333C)
            )
        }
        Button(
            onClick = {
                onSave.invoke(addMoreState.hashTagList)
            },
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6900))

        ) {
            Text("Save", fontWeight = FontWeight.W400)
        }
    }

}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun AddMoreOptionBottomSheetPreview() {
    MoreOptionBottomSheet(
        showSheet = true, addMoreState = HashTagsModel(),

        onSave = {}, onDismiss = {}) {

    }
}