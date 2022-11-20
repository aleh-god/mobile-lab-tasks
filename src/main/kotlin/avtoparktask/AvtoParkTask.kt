package avtoparktask

/**
Задача 2. Разработать цифровой аналог автопарка

Условия:
- В автопарке должны быть представлены различные виды транспорта (грузовой,
пассажирский, грузопассажирский).

- У каждого транспортного средства должны быть свои характеристики, как
общие (например, год выпуска, марка и модель, вид используемого топлива, его
расход ...), так и специфичные (например, объём кузова и грузоподъёмность
для транспорта, перевозящего грузы, и пассажировместимость – для
перевозящего пассажиров).

- Аналогично с методами: должны быть как общие (например, заправить и
отремонтировать), так и специфичные (например, продезинфицировать салон
для автобусов и опломбировать кузов для грузовиков).

- Грузовые автомобили должны иметь разные типы кузовов и, соответственно,
разные варианты перевозимых грузов (например, в тентованных можно
перевозить промтовары, в рефрижераторах – промтовары и скоропортящиеся
продукты, в цистернах – жидкости и т.д.).

- Также следует предусмотреть выполнение заказов на перевозки. Заказ должен
включать начальный и конечный пункты, а также объём, вес и тип груза для
грузоперевозок или количество пассажиров для пассажироперевозок. Должна
быть возможность погрузить и разгрузить заказ в/из конкретной машины. У
каждого транспортного средства должна быть возможность просмотреть
свободную грузоподъёмность, объём (либо количество мест) и заказы, которые
на данный момент загружены в машину.

Результат разработки:
- Объектно-ориентированное описание автопарка.
- Требуется промоделировать работу (создать несколько транспортных средств,
заправить и обслужить их, загрузить/разгрузить несколько заказов). В идеале –
с использованием автоматизированного цикла.

Оформление результата выполнения задачи:
- Листинг описания и примера с моделированием работы в одном TXT-файле.
 */

enum class EngineType(
    val maxFuelCapacity: Int
) {
    DIESEL(200),
    GASOLINE(100)
}

sealed class Cargo {
    data class Goods(val valueInTons: Int) : Cargo()
    data class Liquid(val valueInLitres: Int) : Cargo()
    data class Passengers(val valueInCount: Int) : Cargo()
}

data class Route(
    val from: String,
    val toDestination: String,
    val cargo: Cargo
)

interface VehicleTask {

    fun doSolution()

    class Base() : VehicleTask {

        override fun doSolution() {
            val badLiquid = Route(
                from = "Factory",
                toDestination = "River",
                cargo = Cargo.Liquid(2000)
            )

            val trash = Route(
                from = "City",
                toDestination = "Rubbish dump",
                cargo = Cargo.Goods(20)
            )

            val zombie = Route(
                from = "ZombieLand",
                toDestination = "ZombieLand",
                cargo = Cargo.Passengers(10)
            )

            val furyRoad = Route(
                from = "Citadel",
                toDestination = "Green Land",
                cargo = Cargo.Liquid(1000)
            )

            val rockets = Route(
                from = "Vehicle",
                toDestination = "Enemy",
                cargo = Cargo.Goods(2)
            )

            val batmanComeHome = Route(
                from = "City",
                toDestination = "Wayne Manor",
                cargo = Cargo.Passengers(1)
            )

            val batMobile = VehicleStyle.MultiVehicleStyle(
                model = "BatMobile",
                engineType = EngineType.GASOLINE,
                body = BodyStyles.CargoStyles.Container(2),
                cabin = BodyStyles.Bus(1)
            )

            val battleTrack = VehicleStyle.FreightStyle(
                model = "Furiosa battle track",
                engineType = EngineType.DIESEL,
                body = BodyStyles.CargoStyles.Tank(10000)
            )

            val avtobot = VehicleStyle.FreightStyle(
                model = "Optimus Prime",
                engineType = EngineType.GASOLINE,
                body = BodyStyles.CargoStyles.Container(20)
            )

            val bus = VehicleStyle.BusStyle(
                model = "School bus",
                engineType = EngineType.DIESEL,
                body = BodyStyles.Bus(30)
            )

            val vehicleList = listOf(batMobile, battleTrack, avtobot, bus)
            val routes = listOf(furyRoad, trash, rockets, rockets, zombie, zombie, batmanComeHome, badLiquid)
            val destinations = listOf("Enemy", "Green Land", "Citadel", "Wayne Manor", "Rubbish dump", "ZombieLand", "River")

            vehicleList
                .onEach { vehicle ->
                    println("${vehicle.model}: fuel = ${vehicle.fuel}")
                    vehicle.refuel()
                    println("${vehicle.model}: refuel = ${vehicle.fuel}")

                    when(vehicle) {
                        is VehicleStyle.BusStyle -> {
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")

                            routes
                                .shuffled()
                                .forEach {
                                    val resultUpload = vehicle.uploadCargo(it)
                                    println("${vehicle.model}: Route = ${it.toDestination} resultUpload = $resultUpload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")

                            destinations
                                .shuffled()
                                .forEach {
                                    val resultDownload = vehicle.downloadCargo(it)
                                    println("${vehicle.model}: Destinations = $it resultDownload = $resultDownload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")
                            vehicle.cleanInterior()
                        }
                        is VehicleStyle.FreightStyle -> {
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")

                            routes
                                .shuffled()
                                .forEach {
                                    val resultUpload = vehicle.uploadCargo(it)
                                    println("${vehicle.model}: Route = ${it.toDestination} resultUpload = $resultUpload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")
                            vehicle.lockCargo()

                            destinations
                                .shuffled()
                                .forEach {
                                    val resultDownload = vehicle.downloadCargo(it)
                                    println("${vehicle.model}: Destinations = $it resultDownload = $resultDownload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkFreeCapacity = ${vehicle.checkFreeCapacity()}")
                        }
                        is VehicleStyle.MultiVehicleStyle -> {

                            println("${vehicle.model}: checkCargoFreeCapacity = ${vehicle.checkCargoFreeCapacity()}, checkCabinFreeCapacity = ${vehicle.checkCabinFreeCapacity()}")

                            routes
                                .shuffled()
                                .forEach {
                                    val resultUpload = vehicle.uploadCargo(it)
                                    println("${vehicle.model}: Route = ${it.toDestination} resultUpload = $resultUpload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkCargoFreeCapacity = ${vehicle.checkCargoFreeCapacity()}, checkCabinFreeCapacity = ${vehicle.checkCabinFreeCapacity()}")
                            vehicle.lockCargo()

                            destinations
                                .shuffled()
                                .forEach {
                                    val resultDownload = vehicle.downloadCargo(it)
                                    println("${vehicle.model}: Destinations = $it resultDownload = $resultDownload")
                                }

                            println("${vehicle.model}: Routes = ${vehicle.getRoutes()}")
                            println("${vehicle.model}: checkCargoFreeCapacity = ${vehicle.checkCargoFreeCapacity()}, checkCabinFreeCapacity = ${vehicle.checkCabinFreeCapacity()}")
                            vehicle.cleanInterior()
                        }
                    }
                    println()
                }
        }
    }
}
