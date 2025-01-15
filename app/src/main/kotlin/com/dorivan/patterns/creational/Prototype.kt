package com.dorivan.patterns.creational

/*
Used to copy objects
 */

// Create a simple interface with just clone method
interface Prototype<T>{
    fun clone(): T
}

// Implement this clone method to each class that you want to clone
class Car(
    private val brand: Brands,
    private val color: Colors
): Prototype<Car> {
    enum class Brands { HYUNDAI, MITSUBISHI, SUZUKI }
    enum class Colors { BLACK, WHITE, GRAY }

    // You need to declare a secondary constructor that receives the class and creates a new one
    constructor(car: Car): this(car.brand, car.color)

    override fun clone(): Car = Car(this)
}

fun main() {
    val blackCar = Car(Car.Brands.HYUNDAI, Car.Colors.BLACK)
    val clonedBlackCar = blackCar.clone()

    // When we print is possible to see that instances are different
    println(blackCar)
    println(clonedBlackCar)

}