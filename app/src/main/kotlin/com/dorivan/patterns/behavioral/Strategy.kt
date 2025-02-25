package com.dorivan.patterns.behavioral

import com.dorivan.patterns.creational.PaymentType
import java.math.BigDecimal

/*
Use it when a class make something in different ways. You must separate each different way in one class
 */

data class PaymentData(val amount: BigDecimal, val type: Type) {
    enum class Type { CREDIT_CARD, DEBIT_CARD, PIX }
}

// Interface to create strategies
interface PaymentStrategy {
    fun execute(paymentData: PaymentData)
}

// Concrete strategy implementation
class CreditCardPaymentStrategy : PaymentStrategy {
    override fun execute(paymentData: PaymentData) {
        // all logic here
        println("I'm paying using ${paymentData.type}")
    }
}
class DebitCardPaymentStrategy : PaymentStrategy {
    override fun execute(paymentData: PaymentData) {
        // all logic here
        println("I'm paying using ${paymentData.type}")
    }
}
class PixPaymentStrategy : PaymentStrategy {
    override fun execute(paymentData: PaymentData) {
        // all logic here
        println("I'm paying using ${paymentData.type}")
    }
}

// Context manager
class PaymentContext {
    private lateinit var strategy: PaymentStrategy

    fun setStrategy(strategy: PaymentStrategy) {
        this.strategy = strategy
    }

    fun execute(paymentData: PaymentData) {
        strategy.execute(paymentData)
    }
}

fun main() {
    val manager = PaymentContext()
    val paymentData = PaymentData(amount = 10.55.toBigDecimal(), type = PaymentData.Type.CREDIT_CARD)

    val strategy = when(paymentData.type) {
        PaymentData.Type.CREDIT_CARD -> CreditCardPaymentStrategy()
        PaymentData.Type.DEBIT_CARD -> DebitCardPaymentStrategy()
        PaymentData.Type.PIX -> PixPaymentStrategy()
    }

    manager.setStrategy(strategy)
    manager.execute(paymentData)
}