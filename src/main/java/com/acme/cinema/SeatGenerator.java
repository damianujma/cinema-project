package com.acme.cinema;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class SeatGenerator
{
    private SeatGenerator()
    {
    }

    /*
     * Generates 260 free seats indicated by a letter (A-Z) and a number (1-10)
     */
    public static Set<Seat> generateFreeSeats()
    {
        Set<Seat> seats = new HashSet<>();
        IntStream.rangeClosed('A', 'Z').forEach(column ->
            IntStream.rangeClosed(1, 10).forEach(row ->
                seats.add(new Seat(SeatStatus.FREE, (char) column + String.valueOf(row)))));
        return Collections.unmodifiableSet(seats);
    }
}
