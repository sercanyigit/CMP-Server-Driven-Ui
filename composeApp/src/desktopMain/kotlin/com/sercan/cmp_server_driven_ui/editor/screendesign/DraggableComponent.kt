package com.sercan.cmp_server_driven_ui.editor.screendesign

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.desktop.DesktopScreenRenderer
import com.sercan.cmp_server_driven_ui.components.mobil.models.Position
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableComponent(
    component: UiComponent,
    isSelected: Boolean,
    onSelected: () -> Unit,
    onMoved: (Position) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier
            .width(component.position.width.dp)
            .height(
                when (component) {
                    is TextComponent, is TextFieldComponent -> component.position.height.dp + 30.dp
                    else -> component.position.height.dp
                }
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) ColorUtil.Primary else ColorUtil.Border,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelected() }
            .padding(8.dp)
    ) {
        // Tüm componentler render edilecek
        DesktopScreenRenderer(
            component,
            onStateChanged = {}
        )

        // Silme ikonu
        IconButton(
            onClick = { showDeleteConfirmation = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Sil",
                tint = ColorUtil.Secondary
            )
        }
    }

    // Silme onayı için BottomSheet
    if (showDeleteConfirmation) {
        ModalBottomSheet(
            onDismissRequest = { showDeleteConfirmation = false },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Uyarı",
                    tint = ColorUtil.Primary,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Bu bileşeni silmek istediğinizden emin misiniz?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = ColorUtil.TextPrimary
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showDeleteConfirmation = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ColorUtil.Primary
                        )
                    ) {
                        Text("İptal")
                    }

                    Button(
                        onClick = {
                            onDelete()
                            showDeleteConfirmation = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorUtil.Primary,
                            contentColor = ColorUtil.Background
                        )
                    ) {
                        Text("Evet, Sil")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}