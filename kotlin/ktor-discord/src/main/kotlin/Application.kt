package com.milosz000

import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.events
import io.ktor.server.application.*
import java.util.*

private val BOT_TOKEN = try {
    ClassLoader.getSystemResource("bot-token.txt").readText().trim()
} catch (error: Exception) {
    throw RuntimeException("Failed to load bot token. Make sure to create a file named bot-token.txt in" +
            " src/main/resources and paste the bot token into that file.", error)
}

private val productMap: Map<String, List<String>> = mapOf(
    "food" to listOf("Burger", "Pizza", "Sandwich", "Sushi", "Ramen"),
    "fashion" to listOf("Nike Air Max 90", "Adidas Samba", "Nike Cortez", "Vans Authentic"),
    "furniture" to listOf("Table", "Desk", "Chair", "Lamp"),
    "electronics" to listOf("Tablet", "Laptop", "Mobile phone", "PC")
)


suspend fun main(args: Array<String>) {
    bot(BOT_TOKEN) {
        events {
            onMessageCreate { message ->
                println("Received message: ${message.content}")

                if (message.author.isBot == true) return@onMessageCreate

                if (message.content == "ping") {
                    message.reply("pong")
                }

                if (message.content.lowercase() == "categories") {
                    val categories = productMap.keys.joinToString(",")
                    message.reply("Available categories: $categories")
                }

                val categoryRequest = message.content.split(" ")
                if (categoryRequest.size == 2 && categoryRequest[1] == "products") {
                    val category = categoryRequest[0].lowercase(Locale.getDefault())
                    val products = productMap[category]

                    if (products != null) {
                        message.reply("Available products of $category: ${products.joinToString(", ")}")
                    } else {
                        message.reply("$category category cannot be found!")
                    }
                }

            }
        }
    }
}

fun Application.module() {
    configureRouting()
}
