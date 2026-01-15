package com.gmail.wizaripost.json_converter

import com.gmail.wizaripost.json_converter.service.JsonConverter
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JsonConverterApplication {

    @Bean
    fun commandLineRunner(jsonConverter: JsonConverter): CommandLineRunner {
        return CommandLineRunner { args ->
            val outputDir = if (args.isNotEmpty()) args[0] else "converted"
            val inputDir = "."

            println("Начало конвертации...")
            println("Исходная директория: $inputDir")
            println("Выходная директория: $outputDir")

            val result = jsonConverter.convertDirectory(inputDir, outputDir)

            println("\nРезультаты конвертации:")
            println("Всего файлов: ${result.totalFiles}")
            println("Успешно: ${result.successful}")
            println("С ошибками: ${result.failed}")
            println("Выходная директория: ${result.outputDirectory}")

            if (result.errors.isNotEmpty()) {
                println("\nОшибки:")
                result.errors.forEach { println("- $it") }
            }
        }
    }
}


fun main(args: Array<String>) {
    runApplication<JsonConverterApplication>(*args)
}