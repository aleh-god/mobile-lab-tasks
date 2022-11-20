package avtoparktask

abstract class VehiclePark {

    fun getVehicle(type: String) : VehicleStyle {
        return createVehicle(type).apply {
            refuel()
        }
    }

    protected abstract fun createVehicle(type: String) : VehicleStyle
}

class MultiVehiclePark : VehiclePark() {

    override fun createVehicle(type: String): VehicleStyle =
        when (type) {
            "Batman" -> VehicleStyle.MultiVehicleStyle(
                model = "BatMobile with 2 rockets",
                engineType = EngineType.GASOLINE,
                body = BodyStyles.CargoStyles.Container(2),
                cabin = BodyStyles.Bus(1)
            )
            else -> throw IllegalArgumentException("Wrong vehicle owner")
        }
}

class MonoVehiclePark : VehiclePark() {

    override fun createVehicle(type: String): VehicleStyle =
        when (type) {
            "Furiosa" -> VehicleStyle.FreightStyle(
                model = "Furiosa battle track",
                engineType = EngineType.DIESEL,
                body = BodyStyles.CargoStyles.Tank(10000)
            )
            "Optimus" -> VehicleStyle.FreightStyle(
                model = "Optimus Prime",
                engineType = EngineType.GASOLINE,
                body = BodyStyles.CargoStyles.Container(20)
            )
            "School" -> VehicleStyle.BusStyle(
                model = "School bus",
                engineType = EngineType.DIESEL,
                body = BodyStyles.Bus(30)
            )
            else -> throw IllegalArgumentException("Wrong vehicle owner")
        }
}
