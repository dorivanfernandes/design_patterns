package com.dorivan.patterns.creational

/*
Use builder to "construct" the product step by step. Think when you go to Subway and in each step
you will decide what put or not at your sandwich. This is a builder of sandwich rsrs
 */

// create a builder interface with build methods
interface Builder {
    fun buildBread(bread: Breads)
    fun buildSalad(salad: Salads)
    fun buildCheese(cheese: Cheeses)
    fun buildMeat(meat: Meats)

    enum class Breads { ROLL, WHITE, VEGAN }
    enum class Salads { TOMATO, LETTUCE, ONION, OLIVE }
    enum class Cheeses { MOZZARELLA, PROVOLONE }
    enum class Meats { BEEF, CHICKEN }
}

// create a "product"
data class Sandwich(
    val bread: Builder.Breads?,
    val salad: MutableList<Builder.Salads>,
    val cheese: MutableList<Builder.Cheeses>,
    val meat: Builder.Meats?
)

// Builder implementation (can have more than one, returning different products)
class SandwichBuilder: Builder {
    private var bread: Builder.Breads? = null
    private var salad: MutableList<Builder.Salads> = mutableListOf()
    private var cheese: MutableList<Builder.Cheeses> = mutableListOf()
    private var meat: Builder.Meats? = null
    override fun buildBread(bread: Builder.Breads) {
        this.bread = bread
    }

    override fun buildSalad(salad: Builder.Salads) {
        this.salad.add(salad)
    }

    override fun buildCheese(cheese: Builder.Cheeses) {
        this.cheese.add(cheese)
    }

    override fun buildMeat(meat: Builder.Meats) {
        this.meat = meat
    }

    fun build(): Sandwich = Sandwich(bread, salad, cheese, meat)
}

fun main() {
    val veganSandwich = SandwichBuilder()
    veganSandwich.buildBread(Builder.Breads.VEGAN)
    veganSandwich.buildSalad(Builder.Salads.TOMATO)
    veganSandwich.buildSalad(Builder.Salads.LETTUCE)
    println(veganSandwich.build())

    val chickenSandwich = SandwichBuilder()
    chickenSandwich.buildBread(Builder.Breads.WHITE)
    chickenSandwich.buildMeat(Builder.Meats.CHICKEN)
    chickenSandwich.buildSalad(Builder.Salads.TOMATO)
    chickenSandwich.buildSalad(Builder.Salads.ONION)
    chickenSandwich.buildSalad(Builder.Salads.OLIVE)
    chickenSandwich.buildCheese(Builder.Cheeses.MOZZARELLA)
    println(chickenSandwich.build())
}