package com.acme.cinema;

public final class Seat
{
    private SeatStatus seatStatus;
    private String number;

    public Seat(SeatStatus seatStatus, String number)
    {
        this.seatStatus = seatStatus;
        this.number = number;
    }

    public SeatStatus getSeatStatus()
    {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus)
    {
        this.seatStatus = seatStatus;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Seat{" +
            "seatStatus=" + seatStatus +
            ", number='" + number + '\'' +
            '}';
    }
}

