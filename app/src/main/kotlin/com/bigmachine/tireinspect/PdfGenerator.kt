package com.bigmachine.tireinspect

import android.content.Context
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File

class PdfGenerator {
    fun createPdf(context: Context, inspection: Inspection): File {
        val dir = context.getExternalFilesDir(null) ?: context.filesDir
        val file = File(dir, "inspection_${inspection.id}.pdf")
        val writer = PdfWriter(file)
        val pdfDoc = PdfDocument(writer)
        val document = Document(pdfDoc)

        document.add(
            Paragraph("ОТЧЁТ ИНСПЕКЦИИ ШИН АВТОПАРКА BIGMACHINE")
                .setFontSize(18f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
        )
        document.add(Paragraph("Дата проверки: ${inspection.dateTime}"))
        document.add(Paragraph("Инспектор: ${inspection.inspectorName}"))
        document.add(Paragraph("Номер ТС: ${inspection.plateNumber}"))
        document.add(Paragraph("VIN: ${inspection.vin}"))
        document.add(Paragraph("Модель ТС: ${inspection.truckModel}"))
        document.add(Paragraph("Марка шины: ${inspection.tireBrand}"))
        document.add(Paragraph("Размер шины: ${inspection.tireSize}"))
        document.add(Paragraph("DOT: ${inspection.dotCode}"))
        document.add(Paragraph("Позиция на оси: ${inspection.axlePosition}"))
        document.add(Paragraph("Глубина протектора: ${inspection.treadDepthMm} мм"))
        document.add(Paragraph("Давление: ${inspection.pressureBar} бар"))
        document.add(Paragraph("Пробег ТС: ${inspection.truckMileageKm} км"))
        document.add(Paragraph("Тип груза: ${inspection.cargoType}"))
        document.add(Paragraph("Масса груза: ${inspection.cargoWeightTon} т"))
        document.add(Paragraph("Комментарий: ${inspection.comment}"))
        document.add(Paragraph("СТАТУС: ${inspection.status}").setBold().setFontSize(14f))

        inspection.photoTreadPath?.let { path ->
            val imgFile = File(path)
            if (imgFile.exists()) {
                val img = Image(ImageDataFactory.create(imgFile.absolutePath))
                img.scaleToFit(450f, 300f)
                document.add(Paragraph("\nФото протектора:"))
                document.add(img)
            }
        }
        inspection.photoPressurePath?.let { path ->
            val imgFile = File(path)
            if (imgFile.exists()) {
                val img = Image(ImageDataFactory.create(imgFile.absolutePath))
                img.scaleToFit(450f, 300f)
                document.add(Paragraph("\nФото манометра (давление):"))
                document.add(img)
            }
        }

        document.close()
        return file
    }
}
