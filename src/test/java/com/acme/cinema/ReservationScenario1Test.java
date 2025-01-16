package com.acme.cinema;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationScenario1Test
{
    private static final String SEAT1 = "M5";
    private static final String SEAT2 = "Y3";
    private static final String SEAT3 = "R1";

    private static final String USER = "JOHN_DOE";
    private static final String USER2 = "TOM_SMITH";

    private ReservationService reservationService;

    @BeforeEach
    void setUp()
    {
        reservationService = new ReservationServiceImpl(SeatGenerator.generateFreeSeats());
    }

    @Test
    void shouldReturnPercentageOfFreeSeats()
    {
        reservationService.reserveSeats(USER, List.of(SEAT1, SEAT2));
        var percentageOfFreeSeats = reservationService.percentageOfFreeSeats();

        assertThat(percentageOfFreeSeats).isEqualTo(99);
    }

    @Test
    void shouldReturnCorrectPercentageOfFreeSeatsAfterMultipleReservations()
    {
        reservationService.reserveSeats(USER, List.of(SEAT1, SEAT2));
        reservationService.reserveSeats(USER2, List.of(SEAT3));
        var percentageOfFreeSeats = reservationService.percentageOfFreeSeats();

        assertThat(percentageOfFreeSeats).isEqualTo(99);
    }

    @Test
    void shouldReturnListOfReservedSeatsByClient()
    {
        reservationService.reserveSeats(USER, List.of(SEAT1, SEAT2));
        var reservedSeats = reservationService.getReservedSeatsByClient(USER);

        assertThat(reservedSeats)
            .extracting(Seat::getNumber)
            .containsExactlyInAnyOrder(SEAT1, SEAT2);
    }

    @Test
    void shouldReserveAndReturnListOfReservedSeatsByUser()
    {
        var reservedSeats = reservationService.reserveSeats(USER, List.of(SEAT1, SEAT2));
        assertThat(reservedSeats)
            .extracting(Seat::getNumber)
            .containsExactlyInAnyOrder(SEAT1, SEAT2);

        var percentageOfFreeSeats = reservationService.percentageOfFreeSeats();
        assertThat(percentageOfFreeSeats).isEqualTo(99);

        reservedSeats = reservationService.reserveSeats(USER, List.of(SEAT3));
        assertThat(reservedSeats)
            .extracting(Seat::getNumber)
            .containsExactlyInAnyOrder(SEAT1, SEAT2, SEAT3);

        percentageOfFreeSeats = reservationService.percentageOfFreeSeats();
        assertThat(percentageOfFreeSeats).isEqualTo(99);
    }

    @Test
    void shouldReturnEmptyListWhenNoSeatsAreReserved()
    {
        var reservedSeats = reservationService.getReservedSeatsByClient(USER);
        var percentageOfFreeSeats = reservationService.percentageOfFreeSeats();

        assertThat(reservedSeats).isEmpty();
        assertThat(percentageOfFreeSeats).isEqualTo(100);
    }

    @Test
    void shouldReturnAllClients()
    {
        reservationService.reserveSeats(USER, List.of(SEAT1));
        reservationService.reserveSeats(USER2, List.of(SEAT2));
        var allClients = reservationService.getAllClients();

        assertThat(allClients).containsExactlyInAnyOrder(USER, USER2);
    }

    @Test
    void shouldNotReserveAlreadyReservedSeats()
    {
        reservationService.reserveSeats(USER, List.of(SEAT1));
        var reservedSeats = reservationService.reserveSeats(USER2, List.of(SEAT1));

        assertThat(reservedSeats).isEmpty();
    }

    @Test
    void shouldHandleInvalidSeatNumbersGracefully()
    {
        var reservedSeats = reservationService.reserveSeats(USER, List.of("INVALID_SEAT"));

        assertThat(reservedSeats).isEmpty();
    }
}
