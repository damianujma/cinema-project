package com.acme.cinema;

import java.util.List;
import java.util.Set;

public class ReservationServiceImpl implements ReservationService
{
    private final Set<Seat> seats;

    public ReservationServiceImpl(Set<Seat> seats)
    {
        this.seats = seats;
    }

    @Override
    public Set<Seat> reserveSeats(String client, List<String> numbers)
    {
        // To implement
        return null;
    }

    @Override
    public int percentageOfFreeSeats()
    {
        // To implement
        return 0;
    }

    public Set<Seat> getReservedSeatsByClient(String client)
    {
        // To implement
        return null;
    }

    public Set<String> getAllClients()
    {
        // To implement
        return null;
    }
}
