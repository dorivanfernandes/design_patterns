package com.dorivan.patterns.creational

import kotlin.random.Random

/*
- It's about family of "product". When one item has possible variations

In the example below the product is Header, Body and Footer and their families are English and Portuguese
 */


// Interface to products
interface DocumentHeader { fun content(): String }
interface DocumentBody { fun content(): String }
interface DocumentFooter { fun content(): String }

// concrete implementation to each variation/family of the product
class PortugueseHeader: DocumentHeader {
    override fun content(): String = "Eu sou o cabeçalho em português"
}
class EnglishHeader: DocumentHeader {
    override fun content(): String = "I'm English header"
}
class PortugueseBody: DocumentBody {
    override fun content(): String = "Eu sou o corpo em português"
}
class EnglishBody: DocumentBody {
    override fun content(): String = "I'm English body"
}
class PortugueseFooter: DocumentFooter {
    override fun content(): String = "Eu sou o rodapé em português"
}
class EnglishFooter: DocumentFooter {
    override fun content(): String = "I'm English footer"
}


// Same idea of factory method. Factory methods... one to each product
interface DocumentFactory {
    fun createHeader() : DocumentHeader
    fun createBody() : DocumentBody
    fun createFooter() : DocumentFooter
}

// Factory methods implementation. Implements it to each family
class PortugueseDocumentFactory : DocumentFactory {
    override fun createHeader() = PortugueseHeader()
    override fun createBody() = PortugueseBody()
    override fun createFooter() = PortugueseFooter()
}
class EnglishDocumentFactory : DocumentFactory {
    override fun createHeader() = EnglishHeader()
    override fun createBody() = EnglishBody()
    override fun createFooter() = EnglishFooter()
}

// Generic class do create logic from interfaces. Abstract to no stay in the main
class ApplicationDocumentFactory(
    private val factory: DocumentFactory
) {
    fun createFullDocument() {
        println(factory.createHeader().content())
        println(factory.createBody().content())
        println(factory.createFooter().content())
    }
}

// When the app starts should have a way to choose the option
fun main() {
    val option = Random.nextInt(1, 3)

    val factory = when(option) {
        1 -> PortugueseDocumentFactory()
        2 -> EnglishDocumentFactory()
        else -> throw NotImplementedError()
    }

    ApplicationDocumentFactory(factory).createFullDocument()
}