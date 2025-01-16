package com.acme.cinema;

import java.util.List;
import java.util.Set;

/*
 * Service for reserving seats in a cinema hall
 */
public interface ReservationService
{
    /*
     * Reserves a seat for a client if it is free
     *
     * @param client - the name of the client
     * @param numbers - a list of seat numbers
     *
     * @return a list of all reserved seats by the client
     */
    Set<Seat> reserveSeats(String client, List<String> numbers);

    /*
     * Returns a percentage value of free seats in the cinema hall
     */
    int percentageOfFreeSeats();

    /*
     * Returns a list of all reserved seats by the client
     */
    Set<Seat> getReservedSeatsByClient(String client);

    /*
     * Returns a list of all clients who have reserved seats
     */
    Set<String> getAllClients();

//    /*
//     * Cancels reservation of seats for a client
//     *
//     * @param client - the name of the client
//     * @param numbers - a list of seat numbers
//     *
//     * @return a list of all reserved seats by the client
//     */
//    List<Seat> cancelReservations(String client, List<String> numbers);
}
