package com.bigmachine.tireinspect

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Inspection::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inspectionDao(): InspectionDao
}
