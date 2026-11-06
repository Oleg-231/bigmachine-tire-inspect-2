package com.bigmachine.tireinspect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportsListScreen(
    list: List<Inspection>,
    onViewPdf: (Inspection) -> Unit,
    onSavePdfLocal: (Inspection) -> Unit,
    onSendMail: (Inspection, String) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Список инспекций", fontSize = 22.sp)
        LazyColumn(modifier = Modifier.weight(1f).padding(top = 12.dp)) {
            items(list) { inspect ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("ТС: ${inspect.plateNumber}")
                        Text("Протектор: ${inspect.treadDepthMm} мм | Статус: ${inspect.status}")

                        Button(
                            onClick = { onViewPdf(inspect) },
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text("Показать PDF")
                        }
                        Button(
                            onClick = { onSavePdfLocal(inspect) },
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text("Сохранить PDF")
                        }
                        Button(
                            onClick = { inspect.recipientEmail?.let { onSendMail(inspect, it) } },
                            enabled = !inspect.recipientEmail.isNullOrBlank(),
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = Color.Gray
                            ),
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text("Отправить на почту")
                        }
                    }
                }
            }
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Назад")
        }
    }
}
