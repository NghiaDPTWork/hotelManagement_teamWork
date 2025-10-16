<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Booking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
=======
package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Booking;
import edu.hotel_management.domain.entities.enums.BookingStatus;
import edu.hotel_management.domain.entities.enums.PaymentStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab

/**
 *
 * @author thuannd.dev
 */
public class BookingDAO extends BaseDAO<Booking> {

<<<<<<< HEAD
    // ========= CONSTRUCTOR =========
    public BookingDAO(DataSource dataSource) {
        super(dataSource);
    }

    // ========= ROW MAPPER =========
    @Override
    public Booking mapRowtoObject(ResultSet rs) throws SQLException {
=======
    public BookingDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Booking mapRow(ResultSet rs) throws SQLException {
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab
        return new Booking(
                rs.getInt("BookingID"),
                rs.getInt("GuestID"),
                rs.getInt("RoomID"),
<<<<<<< HEAD
                rs.getObject("CheckInDate", LocalDate.class),
                rs.getObject("CheckOutDate", LocalDate.class),
                rs.getObject("BookingDate", LocalDate.class),
                rs.getString("Status"),
                rs.getInt("TotalGuests"),
                rs.getString("SpecialRequests"),
                rs.getString("PaymentStatus"),
                rs.getObject("CancellationDate", LocalDate.class),
                rs.getString("CancellationReason")
        );
    }

    // ========= QUERY METHODS (SELECT) =========
    public List<Booking> findAll() {
        String sql = "SELECT * FROM BOOKING";
        return query(sql);
    }

    public Optional<Booking> findById(int bookingId) {
        String sql = "SELECT * FROM BOOKING WHERE BookingID = ?";
        List<Booking> bookings = query(sql, bookingId);
        return bookings.isEmpty() ? Optional.empty() : Optional.of(bookings.get(0));
    }

    public List<Booking> findByGuestId(int guestId) {
        String sql = "SELECT * FROM BOOKING WHERE GuestID = ?";
        return query(sql, guestId);
    }
    
    public List<Booking> findByRoomId(int roomId) {
        String sql = "SELECT * FROM BOOKING WHERE RoomID = ?";
        return query(sql, roomId);
    }
    
    public List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM BOOKING WHERE (CheckInDate <= ? AND CheckOutDate >= ?)";
        return query(sql, endDate, startDate);
    }

    // ========= UPDATE METHODS (INSERT, UPDATE, DELETE) =========
    public boolean create(Booking booking) {
        String sql = "INSERT INTO BOOKING (GuestID, RoomID, CheckInDate, CheckOutDate, BookingDate, Status, TotalGuests, SpecialRequests, PaymentStatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return update(sql, 
                booking.getGuestId(), 
                booking.getRoomId(), 
                booking.getCheckInDate(), 
                booking.getCheckOutDate(), 
                LocalDate.now(), 
                "Confirmed", 
                booking.getTotalGuests(), 
                booking.getSpecialRequests(), 
                "Pending"
        ) > 0;
    }

    public boolean update(Booking booking) {
        String sql = "UPDATE BOOKING SET GuestID = ?, RoomID = ?, CheckInDate = ?, CheckOutDate = ?, Status = ?, " +
                     "TotalGuests = ?, SpecialRequests = ?, PaymentStatus = ? WHERE BookingID = ?";
        return update(sql, 
                booking.getGuestId(), 
                booking.getRoomId(), 
                booking.getCheckInDate(), 
                booking.getCheckOutDate(), 
                booking.getStatus(), 
                booking.getTotalGuests(), 
                booking.getSpecialRequests(), 
                booking.getPaymentStatus(),
                booking.getBookingId()
        ) > 0;
    }

    public boolean cancel(int bookingId, String reason) {
        String sql = "UPDATE BOOKING SET Status = 'Cancelled', CancellationDate = ?, CancellationReason = ? WHERE BookingID = ?";
        return update(sql, LocalDate.now(), reason, bookingId) > 0;
    }

    public boolean delete(int bookingId) {
        String sql = "DELETE FROM BOOKING WHERE BookingID = ?";
        return update(sql, bookingId) > 0;
    }
}
=======
                rs.getDate("CheckInDate").toLocalDate(),
                rs.getDate("CheckOutDate").toLocalDate(),
                rs.getDate("BookingDate").toLocalDate(),
                BookingStatus.fromDbValue(rs.getString("Status")),
                rs.getInt("TotalGuests"),
                rs.getString("SpecialRequests") != null ? rs.getString("SpecialRequests") : "",
                PaymentStatus.fromDbValue(rs.getString("PaymentStatus")),
                rs.getDate("CancellationDate") != null ? rs.getDate("CancellationDate").toLocalDate() : null,
                rs.getString("CancellationReason") != null ? rs.getString("CancellationReason") : ""
        );
    }

    public List<Booking> findAll() {
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B");
    }

    public Optional<Booking> findById(int id) {
        List<Booking> bookings = query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "WHERE B.BookingID = ?", id);
        return bookings.stream().findFirst();
    }

    public List<Booking> findByStatus(BookingStatus status) {
        return query("SELECT\n" +
                "B.BookingID, B.GuestID, B.RoomID,\n" +
                "B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status,\n" +
                "B.TotalGuests, B.SpecialRequests, B.PaymentStatus, B.CancellationDate,\n" +
                "B.CancellationReason FROM BOOKING B\n" +
                "WHERE B.Status = ?", status.getDbValue());
    }
}
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab
