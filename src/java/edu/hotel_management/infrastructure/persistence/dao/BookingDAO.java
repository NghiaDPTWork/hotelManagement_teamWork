package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Booking;
import edu.hotel_management.domain.entities.enums.BookingStatus;
import edu.hotel_management.domain.entities.enums.PaymentStatus;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author thuannd.dev
 */
public class BookingDAO extends BaseDAO<Booking> {

    public BookingDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Booking mapRow(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("BookingID"),
                rs.getInt("GuestID"),
                rs.getInt("RoomID"),
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
