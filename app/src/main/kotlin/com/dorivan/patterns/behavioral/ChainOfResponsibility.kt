package com.dorivan.patterns.behavioral

import java.time.LocalDate


/*
It must be used when you have a lot of steps to take. Each step must be a Handler implementation and decide if
send to the next Handler or not. In the Handler interface can have a method or field that set the next Handler.
 */

data class User(val name: String, val email: String, val birthDate: LocalDate)

// Interface to create validators
interface UserHandler {
    fun setNext(next: UserHandler): UserHandler
    fun execute(data: User): String
}

// This abstract class is optional. I create it to not repeat the setNext and execute code in all concrete validators
abstract class BaseUserHandler: UserHandler {
    private var next: UserHandler? = null
    override fun setNext(next: UserHandler): UserHandler {
        this.next = next
        return next
    }

    override fun execute(data: User): String {
        return next?.execute(data) ?: ""
    }
}

// Concrete validators
class NameValidatorHandler() : BaseUserHandler() {
    override fun execute(data: User): String {
        if (data.name.length < 3 ) {
            return "Name must be at least 3 characters"
        }
        return super.execute(data)
    }
}
class EmailValidatorHandler() : BaseUserHandler() {
    override fun execute(data: User): String {
        if (!data.email.contains('@')) {
            return "Invalid email"
        }
        return super.execute(data)
    }
}
class BirthDateValidatorHandler() : BaseUserHandler() {
    override fun execute(data: User): String {
        if (calculateAge(data) < 18){
            return "Age must be more than 18 years"
        }
        return super.execute(data)
    }

    private fun calculateAge(data: User): Int =
        LocalDate.now().year - data.birthDate.year
}

// config class. Creating the chain (first validate name, after email and after birth date)
class ConfigUserChain {
    private val handlers: UserHandler = handlers()

    private fun handlers(): UserHandler {
        return NameValidatorHandler().apply {
            setNext(EmailValidatorHandler())
                .setNext(BirthDateValidatorHandler())
        }
    }

    fun validateUserData(data: User): String {
        return handlers.execute(data)
    }
}

// When find an error doesn't pass to the next validator
fun main() {
    val chain = ConfigUserChain()
    mapOf(
        "name is invalid" to User(name = "Pe", email = "email", birthDate = LocalDate.parse("1994-10-13")),
        "email is invalid" to User(name = "Pedro", email = "email", birthDate = LocalDate.parse("1994-10-13")),
        "birth date is invalid" to User(name = "Pedro", email = "email@email.com", birthDate = LocalDate.now()),
        "user is valid. It pass all of validations successfully" to User(name = "Pedro", email = "email@email.com", birthDate = LocalDate.parse("1994-10-13"))
    ).forEach { (scenario, user) ->
        println("When the $scenario must return validation: ${chain.validateUserData(user)}")
    }

}