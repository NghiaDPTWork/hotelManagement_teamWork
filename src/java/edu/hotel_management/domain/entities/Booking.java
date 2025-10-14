package edu.hotel_management.domain.entities;


import edu.hotel_management.domain.entities.enums.BookingStatus;
import edu.hotel_management.domain.entities.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Booking {
    private int bookingId;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private BookingStatus status;
    private int totalGuest;
    private String specialRequests;
    private PaymentStatus paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;
}
