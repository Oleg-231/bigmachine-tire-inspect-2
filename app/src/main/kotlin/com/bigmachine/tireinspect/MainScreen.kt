package com.bigmachine.tireinspect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    onOpenNewInspection: () -> Unit,
    onOpenReportsList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("BIGMACHINE — Инспекция шин", fontSize = 22.sp)
        Button(onClick = onOpenNewInspection, modifier = Modifier.padding(top = 24.dp)) {
            Text("Новая инспекция")
        }
        Button(onClick = onOpenReportsList, modifier = Modifier.padding(top = 12.dp)) {
            Text("Список отчётов")
        }
    }
}
