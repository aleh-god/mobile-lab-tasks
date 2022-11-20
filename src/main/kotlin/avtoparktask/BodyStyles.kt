package avtoparktask

interface BodyBehavior {

    fun getRoutes(): List<Route>
    fun checkFreeCapacity(): Int
    fun uploadCargo(route: Route): Boolean
    fun downloadCargo(destination: String): List<Cargo>
    fun dropCargo()
}

interface BusBehavior : BodyBehavior {
    fun cleanInterior()
}

interface CargoBehavior : BodyBehavior {
    fun lockCargo()
}

sealed class BodyStyles : BodyBehavior {

    protected val routesList: MutableList<Route> = mutableListOf()

    override fun getRoutes(): List<Route> = routesList

    override fun dropCargo() {
        routesList.clear()
    }

    sealed class CargoStyles : BodyStyles(), CargoBehavior {

        private var _cargoIsLock: Boolean = false
        val cargoIsLock: Boolean get() = _cargoIsLock

        override fun lockCargo() {
            _cargoIsLock = true
        }

        override fun downloadCargo(destination: String): List<Cargo> {
            if (!cargoIsLock) return emptyList()
            return routesList
                .filter { it.toDestination == destination }
                .onEach { routesList.remove(it) }
                .map { it.cargo }
        }

        class Container(
            private val weighCapacityInTons: Int
        ) : CargoStyles() {

            override fun checkFreeCapacity(): Int =
                weighCapacityInTons - routesList.sumOf {
                    when (it.cargo) {
                        is Cargo.Goods -> it.cargo.valueInTons
                        else -> 0
                    }
                }

            override fun uploadCargo(route: Route): Boolean {
                return when (route.cargo) {
                    is Cargo.Goods -> {
                        if (checkFreeCapacity() >= route.cargo.valueInTons) {
                            routesList.add(route)
                            true
                        }
                        else false
                    }
                    else -> false
                }
            }
        }

        class Tank(
            private val valueCapacityInLitres: Int
        ) : CargoStyles() {

            override fun checkFreeCapacity(): Int =
                valueCapacityInLitres - routesList.sumOf {
                    when (it.cargo) {
                        is Cargo.Liquid -> it.cargo.valueInLitres
                        else -> 0
                    }
                }

            override fun uploadCargo(route: Route): Boolean {
                return when (route.cargo) {
                    is Cargo.Liquid -> {
                        if (checkFreeCapacity() >= route.cargo.valueInLitres) {
                            routesList.add(route)
                            true
                        } else false
                    }
                    else -> false
                }
            }
        }
    }

    class Bus(
        private val valueCapacityInPassengers: Int
    ) : BodyStyles(), BusBehavior {

        private var _interiorIsClean: Boolean = true
        val interiorIsClean: Boolean get() = _interiorIsClean

        override fun cleanInterior() {
            _interiorIsClean = true
        }

        override fun checkFreeCapacity(): Int {
            val sumCargo = routesList.sumOf {
                when (it.cargo) {
                    is Cargo.Passengers -> it.cargo.valueInCount
                    else -> 0
                }
            }
            return valueCapacityInPassengers - sumCargo
        }

        override fun uploadCargo(route: Route): Boolean {
            if (!_interiorIsClean) return false
            return when (route.cargo) {
                is Cargo.Passengers -> {
                    if (checkFreeCapacity() >= route.cargo.valueInCount) {
                        routesList.add(route)
                        true
                    } else false
                }
                else -> false
            }

        }

        override fun downloadCargo(destination: String): List<Cargo> =
            routesList
                .filter { it.toDestination == destination }
                .onEach { routesList.remove(it) }
                .map { it.cargo }
                .also { _interiorIsClean = false }
    }
}
