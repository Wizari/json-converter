package com.gmail.wizaripost.json_converter.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmail.wizaripost.json_converter.model.NewFormat
import com.gmail.wizaripost.json_converter.model.OldFormat
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension

@Component
class JsonConverter {

    private val objectMapper: ObjectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(JavaTimeModule())
    }

    fun convertDirectory(inputDir: String = ".", outputDirName: String = "converted"): ConversionResult {
        val currentDir = Paths.get(inputDir).toAbsolutePath()
        val outputDir = currentDir.resolve(outputDirName)

        // Создаем папку для результатов, если её нет
        Files.createDirectories(outputDir)

        val jsonFiles = Files.list(currentDir)
            .filter { Files.isRegularFile(it) && it.extension == "json" }
            .toList()

        var successCount = 0
        var errorCount = 0
        val errors = mutableListOf<String>()

        jsonFiles.forEach { inputFile ->
            try {
                convertFile(inputFile, outputDir)
                successCount++
            } catch (e: Exception) {
                errorCount++
                errors.add("Ошибка при обработке файла ${inputFile.fileName}: ${e.message}")
                e.printStackTrace()
            }
        }

        return ConversionResult(
            totalFiles = jsonFiles.size,
            successful = successCount,
            failed = errorCount,
            outputDirectory = outputDir.toString(),
            errors = errors
        )
    }

    private fun convertFile(inputFile: Path, outputDir: Path) {
        // Читаем новый формат
        val newFormat = objectMapper.readValue(inputFile.toFile(), NewFormat::class.java)

        // Преобразуем в старый формат (адаптируйте эту логику под ваши данные)
        val oldFormat = convertToOldFormat(newFormat)

        // Создаем имя выходного файла
        val outputFileName = "${inputFile.nameWithoutExtension}.json"
        val outputFile = outputDir.resolve(outputFileName)

        // Записываем старый формат
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), oldFormat)
    }

    private fun convertToOldFormat(new: NewFormat): OldFormat {
        // Здесь реализуйте логику преобразования
        // Это пример - адаптируйте под ваши форматы
        return OldFormat(
            identifier = new.id,
            name = new.newName,
            value = new.data.value,
            items = new.data.items,
            createdAt = new.timestamp
        )
    }

    data class ConversionResult(
        val totalFiles: Int,
        val successful: Int,
        val failed: Int,
        val outputDirectory: String,
        val errors: List<String>
    )
}