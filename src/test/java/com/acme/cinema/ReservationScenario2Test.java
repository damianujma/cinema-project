package com.acme.cinema;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;

class ReservationScenario2Test
{

    private static final long NUMBER_OF_THREADS = 10;

    @Test
    void shouldReserveTheSeatsInThreadSafeManner() throws InterruptedException
    {
        assertSingleThreadResultIsTheSameAsMultithreadedOne(10);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(100);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(1000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(10_000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(100_000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(1_000_000);
    }

    private void assertSingleThreadResultIsTheSameAsMultithreadedOne(int numberOfTasks) throws InterruptedException
    {
        Collection<Consumer<ReservationService>> singleThreadTest = generateTasks(numberOfTasks);
        Collection<Consumer<ReservationService>> multiThreadTest = generateTasks(numberOfTasks);

        checkResults(Executors::newSingleThreadExecutor, singleThreadTest);
        checkResults(() -> Executors.newFixedThreadPool(10), multiThreadTest);

    }

    private static void checkResults(
        Supplier<ExecutorService> executorSupplier, Collection<Consumer<ReservationService>> tasks) throws InterruptedException
    {
        final ExecutorService executor = executorSupplier.get();
        ReservationService reservationService = new ReservationServiceImpl(SeatGenerator.generateFreeSeats());

        List<Future<?>> futures = tasks.stream()
                .map(task -> executor.submit(() -> task.accept(reservationService)))
            .collect(toUnmodifiableList());

        executor.shutdown();
        boolean terminated = executor.awaitTermination(NUMBER_OF_THREADS, TimeUnit.SECONDS);
        assertThat(terminated).withFailMessage("Reservations not terminated in 10 seconds.")
            .isTrue();

        futures.forEach(f -> assertThatNoException().isThrownBy(f::get));

        var listOfClients = reservationService.getAllClients();
        var allReservedSeats = new ArrayList<>();
        listOfClients.forEach(client -> allReservedSeats.addAll(reservationService.getReservedSeatsByClient(client)));
        assertThat(allReservedSeats)
            .doesNotHaveDuplicates();
        assertThat(reservationService.percentageOfFreeSeats())
            .isEqualTo(99);
    }

    private static Collection<Consumer<ReservationService>> generateTasks(int numberOfTasks)
    {
        return LongStream.range(0, numberOfTasks)
                         .mapToObj(t -> {
                             final String client = "client" + t;
                             return reserve(Arrays.asList("A1", "M5", "Y5"), client);
                         })
                         .collect(toList());
    }

    private static Consumer<ReservationService> reserve(List<String> seats, String client)
    {
        return reservationService -> reservationService.reserveSeats(client, seats);
    }
}
