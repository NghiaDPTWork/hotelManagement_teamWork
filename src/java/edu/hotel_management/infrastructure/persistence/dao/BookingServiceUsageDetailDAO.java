package edu.hotel_management.infrastructure.persistence.dao;


import edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookingServiceUsageDetailDAO extends BaseDAO<BookingServiceUsageDetailViewModel>{

    public BookingServiceUsageDetailDAO(DataSource ds) { super(ds); }

    @Override
    public BookingServiceUsageDetailViewModel mapRow(ResultSet rs) throws SQLException {
        return new BookingServiceUsageDetailViewModel(

        );
    }

    public Optional<BookingServiceUsageDetailViewModel> findById(int bookingServiceId) {
        List<BookingServiceUsageDetailViewModel> bookings = query("SELECT\n" +
                "BS.Booking_Service_ID, BS.BookingID, B.GuestID,\n" +
                "G.FullName, G.Phone, G.Email, G.IDNumber,\n" +
                "R.RoomID, R.RoomNumber, B.Status AS BookingStatus,\n" +
                "S.ServiceID, S.ServiceName, BS.ServiceDate, \n" +
                "BS.Status AS BookingServiceStatus, BS.AssignedStaffID,\n" +
                "BS.RequestTime, BS.CompletionTime, S.Price, BS.Quantity, \n" +
                "(S.Price * BS.Quantity) AS SubTotal\n" +
                "FROM BOOKING_SERVICE BS\n" +
                "JOIN SERVICE S ON BS.ServiceID = S.ServiceID\n" +
                "JOIN BOOKING B ON BS.BookingID = B.BookingID\n" +
                "JOIN ROOM R ON B.RoomID = R.RoomID\n" +
                "JOIN GUEST G ON B.GuestID = G.GuestID\n" +
                "WHERE BS.Booking_Service_ID = ?", bookingServiceId);
        return bookings.stream().findFirst();
    }
}
