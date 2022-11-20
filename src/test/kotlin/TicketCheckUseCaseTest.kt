import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ticketchecktask.TicketCheckUseCase

internal class TicketCheckUseCaseTest {

    private val useCase = TicketCheckUseCase.BaseImplementation()

    @Test
    fun `isWinnigNumbers is correct`() {

        val ticket: String = "12344321"
        val actual = useCase.isWinnigNumbers(ticket)
        val expected = true
        assertEquals(expected, actual)

    }
}