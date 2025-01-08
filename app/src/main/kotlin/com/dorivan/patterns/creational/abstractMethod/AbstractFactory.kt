package com.dorivan.patterns.creational.abstractMethod

enum class Providers { BRADESCO, ITAU }

interface Provider {
    val type: Providers
    fun pix()
    fun card()
    fun bankslip()
}

class Bradesco: Provider {
    override val type: Providers = Providers.BRADESCO

    override fun pix() = println("Creating $type pix")

    override fun card() = println("Creating $type card")

    override fun bankslip() = println("Creating $type bankslip")
}

class Itau: Provider {
    override val type: Providers = Providers.ITAU

    override fun pix() = println("Creating $type pix")

    override fun card() = println("Creating $type card")

    override fun bankslip() = println("Creating $type bankslip")
}

interface ProviderFactory {
    
}