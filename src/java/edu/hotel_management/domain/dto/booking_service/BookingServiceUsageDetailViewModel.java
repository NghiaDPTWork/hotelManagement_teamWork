package edu.hotel_management.domain.dto.booking_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookingServiceUsageDetailViewModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bookingServiceId;
    private int bookingId;
    private int guestId;
    private String guestFullName;
    private String guestPhone;
    private String guestEmail;
    private String guestIdNumber;
    private int roomId;
    private String roomNumber;
    private String bookingStatus;
    private int serviceId;
    private String serviceName;
    private LocalDate serviceDate;
    private String bookingServiceStatus;
    private int assignedStaffId;
    private LocalDateTime requestTime;
    private LocalDateTime completionTime;
    private double servicePrice;
    private int quantity;
    private double subPrice;
}
