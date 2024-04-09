package com.example.dtapp.view.editscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dtapp.R
import com.example.dtapp.models.HabitInfo
import com.example.dtapp.view.theme.Purple40
import com.example.dtapp.viewmodels.EditViewModel

@Composable
fun SaveButton(
    navController: NavController,
    id: Int,
    selectedPriority: String,
    selectedType: String,
    nameText: String,
    descriptionText: String,
    timesText: String,
    periodText: String,
    editViewModel: EditViewModel = viewModel()
) {
    val context = LocalContext.current

    var isNavigationPerformed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = {
                if (!isNavigationPerformed) {
                    val habit = HabitInfo.habitInit(
                        context = context,
                        id = id,
                        selectedPriority = selectedPriority,
                        selectedType = selectedType,
                        nameText = nameText,
                        descriptionText = descriptionText,
                        timesText = timesText,
                        periodText = periodText
                    )

                    editViewModel.addOrUpdate(id, habit)

                    navController.popBackStack()
                    isNavigationPerformed = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40,
                contentColor = Color.White
            )
        ) {
            Text(
                text = ContextCompat.getString(context, R.string.habit_save_button),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isNavigationPerformed = false
        }
    }
}