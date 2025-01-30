package com.dorivan.patterns.behavioral

/*
A mediator class to control operations
 */

// Interface mediator
interface AirportControlTowerMediator {
    fun requestLanding(airplane: Airplane): Boolean
    fun requestTakeOff(airplane: Airplane): Boolean
    fun notifyAirplane(airplane: Airplane, message: String)
}

// Concrete mediator with all the rules to control operations
class AirportControlTower : AirportControlTowerMediator {
    private val airplanesControl: MutableList<Airplane> = mutableListOf()

    override fun requestLanding(airplane: Airplane): Boolean {
        if (airplanesControl.isEmpty()) {
            airplanesControl.add(airplane)
            notifyAirplane(airplane, "Landing authorized")
            return true
        }

        notifyAirplane(airplane, "Landing not authorized")
        return false
    }

    override fun requestTakeOff(airplane: Airplane): Boolean {
        if (airplanesControl.contains(airplane)) {
            airplanesControl.remove(airplane)
            notifyAirplane(airplane, "Take off authorized")
            return true
        }
        notifyAirplane(airplane, "Take off not authorized")
        return false
    }

    override fun notifyAirplane(airplane: Airplane, message: String) {
        println("Flight ${airplane.flightCode} $message")
        println("-----------")
    }
}

// Component
class Airplane(
    val flightCode: String,
    private val airportControlTowerMediator: AirportControlTowerMediator
){
    fun land() {
        println("$flightCode requesting landing..." )
        airportControlTowerMediator.requestLanding(this)
    }

    fun takeOff() {
        println("$flightCode requesting take off...")
        airportControlTowerMediator.requestTakeOff(this)
    }
}

fun main() {
    val tower = AirportControlTower()

    val flight1002 = Airplane("fight 1002", tower)
    val flight1005 = Airplane("fight 1005", tower)

    flight1002.land()
    flight1005.land()

    flight1002.takeOff()
    flight1005.land()


}