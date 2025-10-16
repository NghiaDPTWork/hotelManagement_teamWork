/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.application.service;

import edu.hotel_management.domain.entities.Booking;
import edu.hotel_management.infrastructure.persistence.dao.BookingDAO;
import edu.hotel_management.infrastructure.persistence.dao.RoomDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author TR_NGHIA
 */
public class BookingService {

    private final BookingDAO bookingDAO;
    private final RoomDAO roomDAO;

    public BookingService(BookingDAO bookingDAO, RoomDAO roomDAO) {
        this.bookingDAO = bookingDAO;
        this.roomDAO = roomDAO;
    }

    // =========================================================================
    // SECTION: TẠO BOOKING MỚI
    // =========================================================================
    public String createBooking(Booking booking) {
        // 1. Kiểm tra ngày check-in phải trước ngày check-out
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate()) || booking.getCheckInDate().isEqual(booking.getCheckOutDate())) {
            return "Check-out date must be after check-in date.";
        }

        // 2. Kiểm tra xem phòng có tồn tại không
        if (!roomDAO.findById(booking.getRoomId()).isPresent()) {
            return "Room does not exist.";
        }

        // 3. Nếu tất cả đều hợp lệ, tiến hành tạo booking
        boolean isSuccess = bookingDAO.create(booking);

        if (isSuccess) {
            // Cập nhật trạng thái phòng thành "Occupied"
            roomDAO.updateStatus(booking.getRoomId(), "Occupied");
            return null; // Không có lỗi
        } else {
            return "An error occurred while creating the booking.";
        }
    }

    // =========================================================================
    // SECTION: CÁC PHƯƠNG THỨC QUERY 
    // =========================================================================
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }

    public Optional<Booking> getBookingById(int bookingId) {
        return bookingDAO.findById(bookingId);
    }

    public List<Booking> getBookingsByGuest(int guestId) {
        return bookingDAO.findByGuestId(guestId);
    }

    // =========================================================================
    // SECTION: HỦY BOOKING
    // =========================================================================
    public String cancelBooking(int bookingId, String reason) {
        Optional<Booking> bookingOpt = bookingDAO.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            return "Booking not found.";
        }

        Booking booking = bookingOpt.get();
        if (booking.getStatus().equals("Cancelled")) {
            return "This booking has already been cancelled.";
        }

        boolean isSuccess = bookingDAO.cancel(bookingId, reason);

        if (isSuccess) {
            roomDAO.updateStatus(booking.getRoomId(), "Available");
            return null;
        } else {
            return "Failed to cancel the booking.";
        }
    }

}
