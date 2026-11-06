package com.bigmachine.tireinspect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

@Composable
fun PdfPreviewScreen(
    pdfFile: File,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PDFView(it, null).apply {
                    fromFile(pdfFile)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .load()
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Назад")
        }
    }
}
