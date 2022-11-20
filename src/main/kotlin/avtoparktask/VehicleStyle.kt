package avtoparktask

interface VehicleBehavior {

    fun refuel()
}

sealed class VehicleStyle(
    private val engineType: EngineType,
    val model: String
) : VehicleBehavior {

    private var _fuel: Int = 0 // The fuel expenditure will implement in next task.
    val fuel: Int get() = _fuel

    override fun refuel() {
        _fuel = engineType.maxFuelCapacity
    }

    class FreightStyle(
        model: String,
        engineType: EngineType,
        private val body: CargoBehavior
    ) : VehicleStyle(
        model = model,
        engineType = engineType
    ), CargoBehavior by body

    class BusStyle(
        model: String,
        engineType: EngineType,
        private val body: BusBehavior
    ) : VehicleStyle(
        model = model,
        engineType = engineType
    ), BusBehavior by body

    class MultiVehicleStyle(
        model: String,
        engineType: EngineType,
        private val cabin: BusBehavior,
        private val body: CargoBehavior
    ) : VehicleStyle(
        model = model,
        engineType = engineType
    ) {

        fun checkCargoFreeCapacity(): Int {
            return body.checkFreeCapacity()
        }

        fun checkCabinFreeCapacity(): Int {
            return cabin.checkFreeCapacity()
        }

        fun lockCargo() {
            return body.lockCargo()
        }

        fun cleanInterior() {
            cabin.cleanInterior()
        }

        fun uploadCargo(route: Route): Boolean {
            return when (route.cargo) {
                is Cargo.Goods, is Cargo.Liquid -> body.uploadCargo(route)
                is Cargo.Passengers -> cabin.uploadCargo(route)
            }
        }

        fun downloadCargo(destination: String): List<Cargo> {
            return getRoutes().filter { it.toDestination == destination }.flatMap {
                when (it.cargo) {
                    is Cargo.Goods, is Cargo.Liquid -> body.downloadCargo(destination)
                    is Cargo.Passengers -> cabin.downloadCargo(destination)
                }
            }
        }

        fun dropCargo() {
            cabin.dropCargo()
            body.dropCargo()
        }

        fun getRoutes(): List<Route> =
            cabin.getRoutes() + body.getRoutes()
    }
}