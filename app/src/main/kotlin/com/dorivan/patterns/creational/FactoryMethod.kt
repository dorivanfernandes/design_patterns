package com.dorivan.patterns.creational

/*
It's a "Creator" interface that has a factory method. This factory method returns a "product" that is a concrete implementation
of product interface. Each creator implementation can override the factory method and return a concrete product
 */

enum class Providers { ADYEN, PAGARME }
enum class PaymentType {PIX, BANKSLIP}

// interface "product"
interface Provider {
    val type: Providers
    fun createPayment()
}

// concrete "product"
class Adyen: Provider {
    override val type: Providers = Providers.ADYEN
    override fun createPayment(): Unit = println("Creating at $type ...")
}

// concrete "product"
class Pagarme: Provider {
    override val type: Providers = Providers.PAGARME
    override fun createPayment(): Unit = println("Creating at $type ...")
}

// Creator interface, declaring factory method (in this case getProvider())
interface PaymentFactory {
    // factory Method. Each implementation can return any Provider Type
    fun getProvider(): Provider
    fun createPayment(): String
}

// concrete creator, overriding factory method and returning concrete "product"
class Pix: PaymentFactory {
    override fun getProvider(): Provider = Adyen()
    override fun createPayment(): String {
        val provider = getProvider()
        provider.createPayment()
        return "${javaClass.simpleName} Payment was created by ${provider.type} provider"
    }
}

// concrete creator, overriding factory method and returning concrete "product"
class Bankslip: PaymentFactory {
    override fun getProvider(): Provider = Pagarme()
    override fun createPayment(): String {
        val provider = getProvider()
        provider.createPayment()
        return "${javaClass.simpleName} Payment was created by ${provider.type} provider"
    }
}


class Pay() {
    fun createPayment(type: PaymentType) = getPaymentType(type).createPayment()
    // made a switch here just to simplify the example. There are other ways to implement it
    private fun getPaymentType(type: PaymentType): PaymentFactory =
        when(type) {
            PaymentType.BANKSLIP -> Bankslip()
            PaymentType.PIX -> Pix()
        }
}

fun main() {
    val buy: Pay = Pay()

    val result = buy.createPayment(PaymentType.PIX)

    println(result)
}