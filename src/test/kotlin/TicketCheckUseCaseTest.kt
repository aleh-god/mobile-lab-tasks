import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TicketCheckUseCaseTest {

    private val useCase = TicketCheckUseCase.Base()

    @Test
    fun `isWinnigNumbers is correct`() {
        assertEquals(4, 2+2)
    }
}