package com.dorivan.patterns.behavioral

import java.lang.Thread.sleep
import java.math.BigDecimal
import java.util.concurrent.Semaphore

/*
Basically, it is a state machine. Each state is a class
 */

data class Payment(
    val id: Int,
    val value: BigDecimal
)

// State interface
interface PaymentState {
    fun changeState(paymentManager: PaymentManager)
    fun processPayment(payment: Payment)
}

// Concrete states implementations
class PendingState: PaymentState {
    override fun changeState(paymentManager: PaymentManager) {
        paymentManager.changeState(AuthorizedState())
    }

    override fun processPayment(payment: Payment) {
        println("I'm pending")
    }

}
class AuthorizedState: PaymentState {
    override fun changeState(paymentManager: PaymentManager) {
        paymentManager.changeState(CapturedState())
    }

    override fun processPayment(payment: Payment) {
        println("I'm authorized")
    }

}
class CapturedState: PaymentState {
    override fun changeState(paymentManager: PaymentManager) {}

    override fun processPayment(payment: Payment) {
        println("I'm captured")
    }

}

// Manage the state
class PaymentManager {
    private var state: PaymentState = PendingState()

    fun changeState(newState: PaymentState) {
        state = newState
    }

    fun processPayment(payment: Payment) {
        state.processPayment(payment)
        state.changeState(this)
    }
}


fun main() {
    val payment = Payment(10, BigDecimal.TEN)
    val paymentManager = PaymentManager()

    repeat(3) {
        paymentManager.processPayment(payment)
    }
}