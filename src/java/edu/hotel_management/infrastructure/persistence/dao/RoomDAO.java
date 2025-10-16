package edu.hotel_management.infrastructure.persistence.dao;

import edu.hotel_management.domain.entities.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author TR_NGHIA
 */
public class RoomDAO extends BaseDAO<Room> {

    // ========= CONSTRUCTOR =========
    public RoomDAO(DataSource dataSource) {
        super(dataSource);
    }

    // ========= ROW MAPPER =========
    @Override
    public Room mapRowtoObject(ResultSet rs) throws SQLException {

        return new Room(
                rs.getInt("RoomID"),
                rs.getString("RoomNumber"),
                rs.getInt("RoomTypeID"),
                rs.getString("Status"),
                rs.getString("TypeName"),
                rs.getInt("Capacity"),
                rs.getDouble("PricePerNight")
        );
    }

    // ========= QUERY METHODS (SELECT) =========
    public List<Room> findAll() {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID";
        return query(sql);
    }

    public Optional<Room> findById(int roomId) {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.RoomID = ?";
        List<Room> rooms = query(sql, roomId);
        return rooms.isEmpty() ? Optional.empty() : Optional.of(rooms.get(0));
    }

    public List<Room> findAllAvailable() {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.Status = 'Available'";
        return query(sql);
    }

    public Optional<Room> findByRoomNumber(String roomNumber) {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.RoomNumber = ?";
        List<Room> rooms = query(sql, roomNumber);
        return rooms.isEmpty() ? Optional.empty() : Optional.of(rooms.get(0));
    }

    public List<Room> findByRoomTypeId(int roomTypeId) {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.RoomTypeID = ?";
        return query(sql, roomTypeId);
    }

    public List<Room> findByStatus(String status) {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.Status = ?";
        return query(sql, status);
    }

    public List<Room> findAvailableRoomsByType(int roomTypeId) {
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight "
                + "FROM ROOM r JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID WHERE r.Status = 'Available' AND r.RoomTypeID = ?";
        return query(sql, roomTypeId);
    }

    public List<Room> findAvailableRoomsByDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        /*
        * - Một booking bị trùng nếu: (StartA < EndB) AND (EndA > StartB)
        * - Ở đây: (CheckInDate của booking < checkOutDate yêu cầu) AND (CheckOutDate của booking > checkInDate yêu cầu
         */
        String sql = "SELECT r.*, rt.TypeName, rt.Capacity, rt.PricePerNight\n"
                + "FROM ROOM r \n"
                + "JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID\n"
                + "WHERE NOT EXISTS ("
                + "    SELECT 6 FROM BOOKING b\n"
                + "    WHERE b.RoomID = r.RoomID -- Liên kết subquery với query chính\n"
                + "          AND b.Status != 'Cancelled' \n"
                + "          AND (b.CheckInDate < ? AND b.CheckOutDate > ?)";

        return query(sql, checkOutDate, checkInDate);
    }

    // ========= UPDATE METHODS (INSERT, UPDATE, DELETE) =========
    public boolean create(Room room) {
        String sql = "INSERT INTO ROOM (RoomNumber, RoomTypeID, Status) VALUES (?, ?, ?)";
        return update(sql, room.getRoomNumber(), room.getRoomTypeId(), room.getStatus()) > 0;
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE ROOM SET RoomNumber = ?, RoomTypeID = ?, Status = ? WHERE RoomID = ?";
        return update(sql, room.getRoomNumber(), room.getRoomTypeId(), room.getStatus(), room.getRoomId()) > 0;
    }

    public boolean delete(int roomId) {
        String sql = "DELETE FROM ROOM WHERE RoomID = ?";
        return update(sql, roomId) > 0;
    }

    public boolean updateStatus(int roomId, String status) {
        String sql = "UPDATE ROOM SET Status = ? WHERE RoomID = ?";
        return update(sql, status, roomId) > 0;
    }

    // ========= UTILITY & COUNTING METHODS =========
    public boolean existsByRoomNumber(String roomNumber) {
        return findByRoomNumber(roomNumber).isPresent();
    }

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM ROOM WHERE Status = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting rooms by status", e);
        }
        return 0;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM ROOM";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting all rooms", e);
        }
        return 0;
    }
}
