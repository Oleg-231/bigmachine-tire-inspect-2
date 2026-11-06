package com.bigmachine.tireinspect

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun InspectionFormScreen(
    treadPhotoPath: String?,
    pressurePhotoPath: String?,
    onTakeTread: () -> Unit,
    onTakePressure: () -> Unit,
    onSave: (Inspection) -> Unit,
    onBack: () -> Unit
) {
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)

    var plate by remember { mutableStateOf("") }
    var vin by remember { mutableStateOf("") }
    var truckModel by remember { mutableStateOf("") }
    var tireBrand by remember { mutableStateOf("") }
    var tireSize by remember { mutableStateOf("") }
    var dot by remember { mutableStateOf("") }
    var axlePos by remember { mutableStateOf("") }
    var treadDepth by remember { mutableStateOf("") }
    var pressure by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var cargoType by remember { mutableStateOf("") }
    var cargoWeight by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var recipientEmail by remember { mutableStateOf("") }

    val bitmapTread = remember(treadPhotoPath) {
        treadPhotoPath?.let { BitmapFactory.decodeFile(it) }
    }
    val bitmapPressure = remember(pressurePhotoPath) {
        pressurePhotoPath?.let { BitmapFactory.decodeFile(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Новая инспекция", fontSize = 22.sp, fontWeight = FontWeight.Medium)

        OutlinedTextField(
            value = plate, onValueChange = { plate = it },
            label = { Text("Номер ТС") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = vin, onValueChange = { vin = it },
            label = { Text("VIN") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = truckModel, onValueChange = { truckModel = it },
            label = { Text("Модель авто") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = tireBrand, onValueChange = { tireBrand = it },
            label = { Text("Марка шины") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = tireSize, onValueChange = { tireSize = it },
            label = { Text("Размер шины") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = dot, onValueChange = { dot = it },
            label = { Text("DOT код") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = axlePos, onValueChange = { axlePos = it },
            label = { Text("Позиция на оси") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = treadDepth, onValueChange = { treadDepth = it },
            label = { Text("Глубина протектора, мм") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = pressure, onValueChange = { pressure = it },
            label = { Text("Давление, бар") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = mileage, onValueChange = { mileage = it },
            label = { Text("Пробег, км") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = cargoType, onValueChange = { cargoType = it },
            label = { Text("Тип груза") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = cargoWeight, onValueChange = { cargoWeight = it },
            label = { Text("Масса груза, т") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )
        OutlinedTextField(
            value = comment, onValueChange = { comment = it },
            label = { Text("Комментарий / дефекты") },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )

        Text("Фотографии (необязательно)", modifier = Modifier.padding(top = 16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    if (cameraPermission.status.isGranted) onTakeTread()
                    else cameraPermission.launchPermissionRequest()
                }) {
                    Text("Фото протектора")
                }
                bitmapTread?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Протектор",
                        modifier = Modifier.size(100.dp).padding(top = 4.dp)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    if (cameraPermission.status.isGranted) onTakePressure()
                    else cameraPermission.launchPermissionRequest()
                }) {
                    Text("Фото давления")
                }
                bitmapPressure?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Манометр",
                        modifier = Modifier.size(100.dp).padding(top = 4.dp)
                    )
                }
            }
        }

        OutlinedTextField(
            value = recipientEmail, onValueChange = { recipientEmail = it },
            label = { Text("Email для отправки отчёта (необязательно)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )

        Button(
            onClick = {
                val tread = treadDepth.toFloatOrNull() ?: 0f
                val status = when {
                    tread < 3f -> "FAULT"
                    tread in 3.0f..3.5f -> "WARNING"
                    else -> "OK"
                }
                val inspect = Inspection(
                    dateTime = MainActivity.nowString(),
                    inspectorName = "Инспектор",
                    plateNumber = plate,
                    vin = vin,
                    truckModel = truckModel,
                    tireBrand = tireBrand,
                    tireSize = tireSize,
                    dotCode = dot,
                    axlePosition = axlePos,
                    treadDepthMm = tread,
                    pressureBar = pressure.toFloatOrNull() ?: 0f,
                    truckMileageKm = mileage.toIntOrNull() ?: 0,
                    cargoType = cargoType,
                    cargoWeightTon = cargoWeight.toFloatOrNull() ?: 0f,
                    allowedAxleLoad = 0f,
                    actualAxleLoad = 0f,
                    hasCut = false,
                    hasHernia = false,
                    cracks = false,
                    unevenWear = "",
                    comment = comment,
                    photoTreadPath = treadPhotoPath,
                    photoPressurePath = pressurePhotoPath,
                    status = status,
                    recipientEmail = recipientEmail.ifBlank { null }
                )
                onSave(inspect)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 18.dp)
        ) {
            Text("Сохранить")
        }
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Назад")
        }
    }
}
