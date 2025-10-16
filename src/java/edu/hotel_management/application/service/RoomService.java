package edu.hotel_management.application.service;

import edu.hotel_management.domain.entities.Room;
import edu.hotel_management.infrastructure.persistence.dao.BookingDAO;
import edu.hotel_management.infrastructure.persistence.dao.RoomDAO;
import edu.hotel_management.presentation.dto.room.RoomPresentationModel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author TR_NGHIA
 */
public class RoomService {

    private final RoomDAO roomDao;
    private final BookingDAO bookingDao; 

    public RoomService(RoomDAO roomDao, BookingDAO bookingDao) {
        this.roomDao = roomDao;
        this.bookingDao = bookingDao;
    }

    // =========================================================================
    // SECTION: PHƯƠNG THỨC CRUD (Create, Read, Update, Delete)
    // =========================================================================
    
    public String createRoom(Room room) {
        if (roomDao.existsByRoomNumber(room.getRoomNumber())) {
            return "Room number already exists.";
        }
        if (room.getStatus() == null || room.getStatus().isEmpty()) {
            room.setStatus("Available");
        }
        return roomDao.create(room) ? null : "An error occurred while creating room.";
    }

    public String updateRoom(Room room) {
        if (!roomDao.findById(room.getRoomId()).isPresent()) {
            return "Room not found.";
        }
        Optional<Room> roomWithSameNumber = roomDao.findByRoomNumber(room.getRoomNumber());
        if (roomWithSameNumber.isPresent() && roomWithSameNumber.get().getRoomId() != room.getRoomId()) {
            return "Room number already exists.";
        }
        return roomDao.updateRoom(room) ? null : "An error occurred while updating room.";
    }

    public String deleteRoom(int roomId) {
        if (!roomDao.findById(roomId).isPresent()) {
            return "Room not found.";
        }
        return roomDao.delete(roomId) ? null : "An error occurred while deleting room.";
    }

    public String updateRoomStatus(int roomId, String status) {
        if (!isValidStatus(status)) {
            return "Invalid room status. Valid statuses: Available, Occupied, Dirty, Maintenance";
        }
        return roomDao.updateStatus(roomId, status) ? null : "An error occurred while updating room status.";
    }
    
    // =========================================================================
    // SECTION: TÌM KIẾM VÀ KIỂM TRA PHÒNG TRỐNG
    // =========================================================================
    public List<RoomPresentationModel> findAvailableRooms(String arrivalDateStr, String departureDateStr) {
        if (arrivalDateStr == null || arrivalDateStr.isEmpty() || departureDateStr == null || departureDateStr.isEmpty()) {
            return getAvailableRooms();
        }

        try {
            LocalDate arrivalDate = LocalDate.parse(arrivalDateStr);
            LocalDate departureDate = LocalDate.parse(departureDateStr);

            if (departureDate.isBefore(arrivalDate) || departureDate.isEqual(arrivalDate)) {
                return Collections.emptyList(); 
            }
            
            return roomDao.findAvailableRoomsByDateRange(arrivalDate, departureDate)
                          .stream()
                          .map(RoomPresentationModel::fromEntity)
                          .collect(Collectors.toList());

        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }

    // =========================================================================
    // SECTION: CÁC PHƯƠNG THỨC QUERY (LẤY DỮ LIỆU)
    // =========================================================================
    
    public List<RoomPresentationModel> getAllRooms() {
        return roomDao.findAll().stream()
                      .map(RoomPresentationModel::fromEntity)
                      .collect(Collectors.toList());
    }
    
    public RoomPresentationModel getRoomById(int id) {
        return roomDao.findById(id)
                      .map(RoomPresentationModel::fromEntity)
                      .orElse(null);
    }
    
    public RoomPresentationModel getRoomByRoomNumber(String roomNumber) {
        return roomDao.findByRoomNumber(roomNumber)
                      .map(RoomPresentationModel::fromEntity)
                      .orElse(null);
    }
    
    public List<RoomPresentationModel> getRoomsByRoomTypeId(int roomTypeId) {
        return roomDao.findByRoomTypeId(roomTypeId).stream()
                      .map(RoomPresentationModel::fromEntity)
                      .collect(Collectors.toList());
    }
    
    public List<RoomPresentationModel> getRoomsByStatus(String status) {
        return roomDao.findByStatus(status).stream()
                      .map(RoomPresentationModel::fromEntity)
                      .collect(Collectors.toList());
    }
    
    public List<RoomPresentationModel> getAvailableRooms() {
        return roomDao.findAllAvailable().stream()
                      .map(RoomPresentationModel::fromEntity)
                      .collect(Collectors.toList());
    }
    
    public List<RoomPresentationModel> getAvailableRoomsByType(int roomTypeId) {
        return roomDao.findAvailableRoomsByType(roomTypeId).stream()
                      .map(RoomPresentationModel::fromEntity)
                      .collect(Collectors.toList());
    }
    
    // =========================================================================
    // SECTION: CÁC PHƯƠNG THỨC ĐẾM
    // =========================================================================
    
    public int countRoomsByStatus(String status) {
        return roomDao.countByStatus(status);
    }
    
    public int countAllRooms() {
        return roomDao.countAll();
    }
    
    // =========================================================================
    // SECTION: HELPER METHODS
    // =========================================================================
    
    private boolean isValidStatus(String status) {
        return status != null && 
               (status.equals("Available") || 
                status.equals("Occupied") || 
                status.equals("Dirty") || 
                status.equals("Maintenance"));
    }
}