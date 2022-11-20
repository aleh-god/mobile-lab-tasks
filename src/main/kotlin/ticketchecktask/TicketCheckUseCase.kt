package ticketchecktask

/**
Задача 1. Проверка лотерейного билета
Написать функцию, которая на вход принимает номер лотерейного билета и
возвращает булевское значение, выигрышный билет или нет.

Входные параметры:
- Функция должна принимать в качестве входного параметра номер (строка)
лотерейного билета, количество цифр которого чётное от 2 до 8.

Условия:
- Выигрышным считается билет, сумма цифр левой половины номера которого
равна сумме цифр правой половины.

Возвращаемый результат:
- только булевское значение: true – в случае, если билет выиграл; false – если не
выиграл.

Оформление результата выполнения задачи:
- Листинг функции в одном TXT-файле.
 **/

interface TicketCheckUseCase {

    fun isWinnigNumbers(numbers: String): Boolean

    class BaseImplementation : TicketCheckUseCase {

        private val digitsLowLimit = 2
        private val digitsHighLimit = 8
        override fun isWinnigNumbers(numbers: String): Boolean {
            val countDigits = numbers.length
            if (countDigits !in digitsLowLimit..digitsHighLimit) throw IllegalArgumentException("Wrong input! Количество цифр лотерейного билета не от 2 до 8.")
            if (countDigits % 2 != 0) throw IllegalArgumentException("Wrong input! Количество цифр лотерейного билета не чётное.")
            val(first, second) = numbers.chunked(countDigits / 2)
            return sumOfNumbers(first) == sumOfNumbers(second)
        }
        private fun sumOfNumbers(digits: String): Int =
            digits
                .map {
                    when(val digit = it.digitToIntOrNull()) {
                        null -> throw IllegalArgumentException("Wrong input! Лотерейный билет не из цифр.")
                        else -> digit
                    }
                }
                .sumOf { it }
    }
}
