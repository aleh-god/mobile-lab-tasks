
/**
Задача 1. Проверка лотерейного билета
Написать функцию, которая на вход принимает номер лотерейного билета и
возвращает булевское значение, выигрышный билет или нет.

Входные параметры:
- Функция должна принимать в качестве входного параметра номер (число)
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

    fun isWinnigNumbers(numbers: Int): Boolean

    class Base() : TicketCheckUseCase {
        override fun isWinnigNumbers(numbers: Int): Boolean {
            TODO("Not yet implemented")
        }

    }
}