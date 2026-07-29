package com.mahmodhota.worldfood3dadventure.game.world.sudan

/**
 * Detailed culinary metadata for Sudanese dishes.
 */
data class DetailedFoodEntry(
    val id: String,
    val name: String,
    val country: String = "Sudan",
    val region: String,
    val description: String,
    val history: String,
    val ingredients: List<String>,
    val nutrition: String,
    val culturalSignificance: String
)

object SudanFoodBookEntries {
    val entries = listOf(
        DetailedFoodEntry(
            id = "sd_kisra",
            name = "Kisra",
            region = "National",
            description = "A thin, fermented flatbread made from sorghum or wheat flour.",
            history = "Kisra has been a staple in Sudan for centuries, evolving from ancient grain processing techniques.",
            ingredients = listOf("Sorghum flour", "Water", "Salt"),
            nutrition = "High in carbohydrates and dietary fiber.",
            culturalSignificance = "The primary bread of Sudan, essential for almost every meal."
        ),
        DetailedFoodEntry(
            id = "sd_ful",
            name = "Ful Medames",
            region = "Across Sudan",
            description = "Cooked fava beans served with vegetable oil, cumin, and various toppings.",
            history = "While of Egyptian origin, the Sudanese version is unique in its spices and bean texture.",
            ingredients = listOf("Fava beans", "Garlic", "Cumin", "Olive oil", "Lemon"),
            nutrition = "Excellent source of protein and iron.",
            culturalSignificance = "A popular breakfast dish that brings families together."
        ),
        DetailedFoodEntry(
            id = "sd_mulah",
            name = "Mulah",
            region = "Various",
            description = "A thick stew or sauce made from dried vegetables or meat, often served with Kisra.",
            history = "A traditional method of preserving seasonal harvest through drying and stewing.",
            ingredients = listOf("Dried okra", "Dried meat", "Onions", "Spices"),
            nutrition = "Rich in minerals and proteins.",
            culturalSignificance = "Represents the resourcefulness of traditional Sudanese cooking."
        ),
        DetailedFoodEntry(
            id = "sd_tagalia",
            name = "Tagalia",
            region = "Central Sudan",
            description = "A rich red stew made with dried meat, onions, and tomato paste.",
            history = "Originating from the central plains, it's a celebratory dish for guests.",
            ingredients = listOf("Minced dried meat", "Fried onions", "Tomato paste", "Ghee"),
            nutrition = "High energy and protein content.",
            culturalSignificance = "A symbol of hospitality and welcoming in Sudanese homes."
        ),
        DetailedFoodEntry(
            id = "sd_agashe",
            name = "Agashe",
            region = "Western Sudan",
            description = "Spicy grilled meat skewers coated in a special peanut and spice mix.",
            history = "Developed by the Hausa and Fulani people, it's become Sudan's most iconic street food.",
            ingredients = listOf("Beef or Lamb", "Ground peanuts", "Ginger", "Chili", "Spices"),
            nutrition = "High in protein and healthy fats from peanuts.",
            culturalSignificance = "The quintessential Sudanese social food, enjoyed at night markets."
        ),
        DetailedFoodEntry(
            id = "sd_sambusa",
            name = "Sambusa",
            region = "Urban Centers",
            description = "Fried triangular pastries filled with minced meat, lentils, or cheese.",
            history = "Introduced through trade routes, adapted with local Sudanese spices.",
            ingredients = listOf("Pastry dough", "Minced meat", "Green onions", "Garlic"),
            nutrition = "Calorie-dense savory snack.",
            culturalSignificance = "A favorite during Ramadan and festive gatherings."
        ),
        DetailedFoodEntry(
            id = "sd_shawaya",
            name = "Shawaya",
            region = "National",
            description = "Grilled chicken prepared with a distinctive Sudanese blend of aromatic spices.",
            history = "A modern classic found in grills across Khartoum and beyond.",
            ingredients = listOf("Whole chicken", "Black pepper", "Cardamom", "Garlic"),
            nutrition = "High quality protein and lean meat.",
            culturalSignificance = "A popular choice for weekend family outings."
        ),
        DetailedFoodEntry(
            id = "sd_gurrasa",
            name = "Gurrasa",
            region = "Northern Sudan",
            description = "A thick, spongy pancake-like bread often topped with stews or honey.",
            history = "A traditional bread from Northern Sudan, predating many modern wheat varieties.",
            ingredients = listOf("Wheat flour", "Yeast", "Baking powder", "Water"),
            nutrition = "Source of complex carbohydrates.",
            culturalSignificance = "Considered a symbol of the strong agricultural heritage of the North."
        ),
        DetailedFoodEntry(
            id = "sd_asida",
            name = "Asida",
            region = "National",
            description = "A smooth, dome-shaped porridge made from sorghum or wheat.",
            history = "Ancient grain preparation used throughout the Nile valley for millennia.",
            ingredients = listOf("Sorghum flour", "Yogurt", "Water"),
            nutrition = "Easy to digest and very filling.",
            culturalSignificance = "The traditional centerpiece for communal dining."
        ),
        DetailedFoodEntry(
            id = "sd_master",
            name = "Sudan Master",
            region = "The Great Nile",
            description = "A masterclass in the rich and ancient flavors of the Sudanese Nile.",
            history = "Celebrating the final milestone of Chapter 1.",
            ingredients = listOf("All Sudanese flavors"),
            nutrition = "Legendary Achievement.",
            culturalSignificance = "Awarded to the most dedicated World Explorers."
        )
    )
}
