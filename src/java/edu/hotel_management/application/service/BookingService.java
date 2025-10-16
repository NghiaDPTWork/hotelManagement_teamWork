<<<<<<< HEAD
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
=======
package edu.hotel_management.application.service;

import edu.hotel_management.domain.dto.booking.BookingDetailViewModel;
import edu.hotel_management.domain.dto.booking.BookingViewModel;
import edu.hotel_management.domain.entities.Booking;
import edu.hotel_management.domain.entities.enums.BookingStatus;
import edu.hotel_management.infrastructure.persistence.dao.BookingDAO;
import edu.hotel_management.infrastructure.persistence.dao.BookingDetailDAO;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class BookingService {
    private final BookingDAO bookingDao;
    private final BookingDetailDAO bookingDetailDao;

    public BookingService(BookingDAO bookingDao, BookingDetailDAO bookingDetailDao) {
        this.bookingDao = bookingDao;
        this.bookingDetailDao = bookingDetailDao;
    }

    // (method reference)
    public List<BookingViewModel> getAllBookings() {
        return bookingDao.findAll().stream()
                .map(BookingViewModel::fromEntity)
                .collect(Collectors.toList());
    }

    public BookingViewModel getBookingById(int id) {
        Booking booking = bookingDao.findById(id).orElse(null);
        return booking != null ? BookingViewModel.fromEntity(booking) : null;
    }

    public List<BookingDetailViewModel> getAllCheckInBookingDetails() {
        return bookingDetailDao.findByStatus(BookingStatus.CHECK_IN);
    }

    public List<BookingDetailViewModel> getCheckInBookingDetailsByGuestName(String name) {
        return bookingDetailDao.findByFullNameAndStatus(name, BookingStatus.CHECK_IN);
    }

    public BookingDetailViewModel getCheckInBookingDetailById(int id) {
        BookingDetailViewModel booking = bookingDetailDao.findById(id).orElse(null);
        return booking != null &&
                booking.getStatus().equalsIgnoreCase(BookingStatus.CHECK_IN.getDbValue())
                ? booking : null;
    }

    public BookingDetailViewModel getCheckInBookingDetailByRoomNumber(String roomNumber) {
        return bookingDetailDao.findByRoomNumberAndStatus(roomNumber, BookingStatus.CHECK_IN).orElse(null);
    }

    public BookingDetailViewModel getCheckInBookingDetailByGuestPhone(String phone) {
        return bookingDetailDao.findByGuestPhoneAndStatus(phone, BookingStatus.CHECK_IN).orElse(null);
    }

    public BookingDetailViewModel getCheckInBookingDetailByGuestIdNumber(String idNumber) {
        return bookingDetailDao.findByGuestIdNumberAndStatus(idNumber, BookingStatus.CHECK_IN).orElse(null);
    }

    public List<BookingDetailViewModel> findBookings(String searchType, String query) throws ServletException {
        if (searchType == null && query == null) {
            return getAllCheckInBookingDetails();
        }
        switch (Objects.requireNonNull(searchType)) {
            case "guestName":
                return getCheckInBookingDetailsByGuestName(query);
            case "roomNumber":
                BookingDetailViewModel byRoom = getCheckInBookingDetailByRoomNumber(query);
                return byRoom == null ? Collections.emptyList() : Collections.singletonList(byRoom);
            case "guestPhone":
                BookingDetailViewModel byPhone = getCheckInBookingDetailByGuestPhone(query);
                return byPhone == null ? Collections.emptyList() : Collections.singletonList(byPhone);
            case "guestIdNumber":
                BookingDetailViewModel byId = getCheckInBookingDetailByGuestIdNumber(query);
                return byId == null ? Collections.emptyList() : Collections.singletonList(byId);
            default:
                throw new ServletException("Invalid search type");
        }
    }
}
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab
