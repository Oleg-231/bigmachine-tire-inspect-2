package com.bigmachine.tireinspect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.Room
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private val pdfGen = PdfGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(this, AppDatabase::class.java, "inspect_db").build()

        setContent {
            MaterialTheme {
                var screen by remember { mutableStateOf("main") }
                var allInspections by remember { mutableStateOf(listOf<Inspection>()) }
                var selectedInspection: Inspection? by remember { mutableStateOf(null) }

                var currentTreadPhoto by remember { mutableStateOf<String?>(null) }
                var currentPressurePhoto by remember { mutableStateOf<String?>(null) }

                when (screen) {
                    "main" -> MainScreen(
                        onOpenNewInspection = {
                            currentTreadPhoto = null
                            currentPressurePhoto = null
                            screen = "form"
                        },
                        onOpenReportsList = {
                            runBlocking { allInspections = db.inspectionDao().getAllInspections() }
                            screen = "reports"
                        }
                    )
                    "form" -> InspectionFormScreen(
                        treadPhotoPath = currentTreadPhoto,
                        pressurePhotoPath = currentPressurePhoto,
                        onTakeTread = { screen = "camera_tread" },
                        onTakePressure = { screen = "camera_pressure" },
                        onSave = { inspect ->
                            runBlocking { db.inspectionDao().insert(inspect) }
                            screen = "main"
                        },
                        onBack = { screen = "main" }
                    )
                    "camera_tread" -> CameraCaptureScreen(
                        onPhotoSaved = { path ->
                            currentTreadPhoto = path
                            screen = "form"
                        },
                        onBack = { screen = "form" }
                    )
                    "camera_pressure" -> CameraCaptureScreen(
                        onPhotoSaved = { path ->
                            currentPressurePhoto = path
                            screen = "form"
                        },
                        onBack = { screen = "form" }
                    )
                    "reports" -> ReportsListScreen(
                        list = allInspections,
                        onViewPdf = { inspection ->
                            selectedInspection = inspection
                            screen = "pdf_preview"
                        },
                        onSavePdfLocal = { inspection ->
                            pdfGen.createPdf(this@MainActivity, inspection)
                        },
                        onSendMail = { inspection, email ->
                            val pdfFile = pdfGen.createPdf(this@MainActivity, inspection)
                            EmailSender.sendEmail(this@MainActivity, pdfFile, email)
                        },
                        onBack = { screen = "main" }
                    )
                    "pdf_preview" -> {
                        val inspect = selectedInspection
                        if (inspect != null) {
                            val pdfFile = pdfGen.createPdf(this@MainActivity, inspect)
                            PdfPreviewScreen(
                                pdfFile = pdfFile,
                                onBack = { screen = "reports" }
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun nowString(): String =
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
    }
}
