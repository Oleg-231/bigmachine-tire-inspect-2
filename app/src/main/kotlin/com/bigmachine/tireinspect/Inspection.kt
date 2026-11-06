package com.bigmachine.tireinspect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspections")
data class Inspection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateTime: String,
    val inspectorName: String,
    val plateNumber: String,
    val vin: String,
    val truckModel: String,
    val tireBrand: String,
    val tireSize: String,
    val dotCode: String,
    val axlePosition: String,
    val treadDepthMm: Float,
    val pressureBar: Float,
    val truckMileageKm: Int,
    val cargoType: String,
    val cargoWeightTon: Float,
    val allowedAxleLoad: Float,
    val actualAxleLoad: Float,
    val hasCut: Boolean,
    val hasHernia: Boolean,
    val cracks: Boolean,
    val unevenWear: String,
    val comment: String,
    val photoTreadPath: String?,
    val photoPressurePath: String?,
    val status: String,
    val recipientEmail: String?
)
