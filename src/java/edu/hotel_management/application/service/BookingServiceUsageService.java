package edu.hotel_management.application.service;

import edu.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import edu.hotel_management.domain.dto.booking_service.BookingServiceViewModel;
import edu.hotel_management.domain.entities.enums.BookingServiceStatus;
import edu.hotel_management.infrastructure.persistence.dao.BookingServiceDAO;
import edu.hotel_management.domain.entities.BookingService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class BookingServiceUsageService {

    private final BookingServiceDAO bookingServiceDao;

    public BookingServiceUsageService(BookingServiceDAO bookingServiceDao) {
        this.bookingServiceDao = bookingServiceDao;
    }

    public BookingServiceViewModel getBookingServiceById(int id) {
        BookingService bookingService = bookingServiceDao.findById(id).orElse(null);
        return bookingService != null ? BookingServiceViewModel.fromEntity(bookingService) : null;
    }

    public BookingServiceViewModel createBookingService(BookingServiceCreateModel bookingServiceCreateModel) {
        int newId = bookingServiceDao.insertBookingService(
                BookingServiceCreateModel.toEntity(bookingServiceCreateModel), BookingServiceStatus.REQUESTED
        );
        if (newId > 0) {
            return getBookingServiceById(newId);
        } else {
            // Handle insert failure: return null or throw an exception as appropriate
            return null;
        }
    }

    public List<BookingServiceViewModel> createBatchBookingService(List<BookingServiceCreateModel> models) {
        List<BookingService> entities = BookingServiceCreateModel.toListEntity(models);
        List<Integer> newIds = bookingServiceDao.insertBatchBookingService(entities, BookingServiceStatus.REQUESTED);
        if (newIds != null && !newIds.isEmpty()) {
            return newIds.stream()
                    .map(this::getBookingServiceById)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
