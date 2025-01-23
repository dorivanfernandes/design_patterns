package com.dorivan.patterns.behavioral

/*
Use it when you have different operations that calls the same "Receiver" (or service)
You must separate the possible commands and call the receiver. You must have an "Invoker" that receives the command,
and it must call the command method
 */

// Command interface
interface TransactionCommand {
    fun execute()
}

// Concrete Command implementations
class DepositCommand(
    private val accountReceiver: AccountReceiver = AccountReceiver()
): TransactionCommand {
    override fun execute()  = accountReceiver.deposit()
}
class WithdrawCommand(
    private val accountReceiver: AccountReceiver = AccountReceiver()
): TransactionCommand {
    override fun execute() = accountReceiver.withdraw()
}

// Receiver or service or class that has the real logic
class AccountReceiver() {
    fun deposit() {
        println("Deposit was successful")
    }

    fun withdraw() {
        println("Withdraw was successful")
    }
}

// Invoker, receiving the command in the constructor
class ATMInvoker(
    private val command: TransactionCommand
) {
    fun makeOperation() = command.execute()
}

fun main() {
    listOf(
        DepositCommand(),
        WithdrawCommand()
    ).forEach {
        ATMInvoker(it).makeOperation()
    }
}