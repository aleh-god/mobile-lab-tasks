import avtoparktask.*

fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    val vehicleTask = VehicleTask.Base()
    vehicleTask.doSolution()
}