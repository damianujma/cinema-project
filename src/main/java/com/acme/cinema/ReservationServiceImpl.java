package com.acme.cinema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationServiceImpl implements ReservationService
{
    private final Set<Seat> seats;
    private final Map<String, Set<Seat>> userReservations = new ConcurrentHashMap<>();

    public ReservationServiceImpl(Set<Seat> initialSeats)
    {
        this.seats = Collections.synchronizedSet(initialSeats);
    }

    @Override
    public synchronized Set<Seat> reserveSeats(String client, List<String> numbers)
    {
        Set<Seat> reservedSeats = userReservations.getOrDefault(client, new HashSet<>());
        seats.stream()
            .filter(seat -> numbers.contains(seat.getNumber()))
            .filter(seat -> seat.getSeatStatus() == SeatStatus.FREE)
            .forEach(seat -> {
                seat.setSeatStatus(SeatStatus.RESERVED);
                reservedSeats.add(seat);
            });
        userReservations.put(client, reservedSeats);
        return reservedSeats;
    }

    @Override
    public int percentageOfFreeSeats()
    {
        synchronized (seats)
        {
            int totalSeats = seats.size();
            long freeSeats = seats.stream().filter(seat -> seat.getSeatStatus() == SeatStatus.FREE).count();
            double percentage = ((double) freeSeats / totalSeats) * 100;
            return (int) Math.round(percentage);
        }
    }

    public Set<Seat> getReservedSeatsByClient(String client)
    {
        synchronized (userReservations) {
            return userReservations.getOrDefault(client, Collections.emptySet());
        }
    }

    public Set<String> getAllClients()
    {
        synchronized (userReservations) {
            return new HashSet<>(userReservations.keySet());
        }
    }

    //    @Override
    //    public List<Seat> cancelReservations(String client, List<String> numbers)
    //    {
    //        return Collections.emptyList();
    //    }
}
