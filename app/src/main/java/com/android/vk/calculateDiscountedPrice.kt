package com.android.vk

fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): String {
    val discountedPrice = originalPrice * (1 - discountPercentage / 100)
    return String.format("%.2f", discountedPrice)
}