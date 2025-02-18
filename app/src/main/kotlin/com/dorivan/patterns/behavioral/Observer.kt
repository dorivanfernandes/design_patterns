package com.dorivan.patterns.behavioral

/*
Allow you to create a subscription to notify multiple objects about each event from observable object.
It works like a pub/sub or CDC.
 */

data class Message(val type: Type, val content: String ) {
    enum class Type { WHATSAPP, SMS, PUSH, EMAIL}
}

// Interface to subscribers
interface MessageObserver {
    fun notify(message: Message)
}

// concrete subscribers implementation
class LogMessageObserver: MessageObserver {
    override fun notify(message: Message) = println("A ${message.type} message arrived! It's content is ${message.content}")
}
class SendMessageObserver: MessageObserver {
    override fun notify(message: Message) = println("A ${message.type} message was sent! It's content is ${message.content}")
}

// Abstract class to publishers (it can be an interface). Here has the methods to subscribe and unsubscribe
abstract class Publisher  {
    protected val messageObservers = mutableListOf<MessageObserver>()
    fun subscribe(messageObserver: MessageObserver) { messageObservers.add(messageObserver) }
    fun unsubscribe(messageObserver: MessageObserver) { messageObservers.remove(messageObserver) }
}

// concrete publisher, notifying all the subscribers after messages was saved
class MessagePublisher(): Publisher() {
    private val messages = mutableListOf<Message>()
    fun saveMessage(message: Message) {
        messages.add(message)
        messageObservers.forEach { it.notify(message) }
    }
}

fun main() {
    // create publisher and add subscribers
    val messagePublisher = MessagePublisher()
    messagePublisher.subscribe(LogMessageObserver())
    messagePublisher.subscribe(SendMessageObserver())

    // just to simulate different messages
    (1 .. 10 ).forEach { _ ->
        val randomToken = (1000 .. 9999).random().toString()
        val message = Message(type = Message.Type.entries.toTypedArray().random(), "Your authentication token is $randomToken")
        messagePublisher.saveMessage(message)
        println("--------------------")
    }
}

