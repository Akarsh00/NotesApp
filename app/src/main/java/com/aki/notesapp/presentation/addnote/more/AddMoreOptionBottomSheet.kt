package com.aki.notesapp.presentation.addnote.more

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aki.notesapp.presentation.addnote.model.AddNotesState
import com.aki.notesapp.presentation.addnote.more.model.AddMoreAction
import com.aki.notesapp.presentation.addnote.more.model.AddMoreState

@Composable
fun AddMoreOptionBottomSheet(
    state: AddNotesState,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit
) {
    val viewModel: AddMoreOptionViewModel = viewModel()
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MoreOptionBottomSheet(
    modifier: Modifier = Modifier,
    showSheet: Boolean,
    addMoreState: AddMoreState,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onAction: (AddMoreAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    if (showSheet) {
        ModalBottomSheet(
            modifier = modifier
                .wrapContentHeight()
                .imePadding(), onDismissRequest = {
                onDismiss.invoke()
            }, sheetState = sheetState
        ) {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("Add Hashtags '#'", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("Write comma separated hashtags ")
                TextField(
                    value = "" + addMoreState.hashTagText,
                    onValueChange = { newValue -> onAction(AddMoreAction.OnTextChange(newValue)) })


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
    addMoreState: AddMoreState,
    onDismiss: () -> Unit,
    onSave: (List<String>) -> Unit,

    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    onDismiss.invoke()
                }, modifier = Modifier
                    .height(44.dp)
                    .weight(1f), shape = RoundedCornerShape(8.dp)

            ) {
                Text(
                    "Cancel",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF2A333C)
                )
            }
            Button(
                onClick = {
                    onSave.invoke(addMoreState.hashTagList)
                },
                modifier = Modifier
                    .height(44.dp)
                    .weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6900))

            ) {
                Text("Save", fontSize = 13.sp, fontWeight = FontWeight.W400)
            }
        }
    }

}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun AddMoreOptionBottomSheetPreview() {
    MoreOptionBottomSheet(
        showSheet = true, addMoreState = AddMoreState(),

        onSave = {}, onDismiss = {}) {

    }
}