<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.hotel_management.domain.entities;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author TR_NGHIA
 */
=======
package edu.hotel_management.domain.entities;


import edu.hotel_management.domain.entities.enums.BookingStatus;
import edu.hotel_management.domain.entities.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab

@AllArgsConstructor
@NoArgsConstructor
@Getter
<<<<<<< HEAD
@Setter
=======
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab
public class Booking {
    private int bookingId;
    private int guestId;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
<<<<<<< HEAD
    private String status;
    private int totalGuests;
    private String specialRequests;
    private String paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;
}
=======
    private BookingStatus status;
    private int totalGuest;
    private String specialRequests;
    private PaymentStatus paymentStatus;
    private LocalDate cancellationDate;
    private String cancellationReason;
}
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab
