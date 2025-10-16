package edu.hotel_management.application.service;

import edu.hotel_management.domain.dto.booking_service.BookingServiceCreateModel;
import edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;
import edu.hotel_management.domain.dto.booking_service.BookingServiceViewModel;
import edu.hotel_management.domain.entities.enums.BookingServiceStatus;
import edu.hotel_management.infrastructure.persistence.dao.BookingServiceDAO;
import edu.hotel_management.domain.entities.BookingService;
import edu.hotel_management.infrastructure.persistence.dao.BookingServiceUsageDetailDAO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author thuannd.dev
 */
public class BookingServiceUsageService {

    private final BookingServiceDAO bookingServiceDao;
    private final BookingServiceUsageDetailDAO bookingServiceUsageDetailDao;

    public BookingServiceUsageService(BookingServiceDAO bookingServiceDao, BookingServiceUsageDetailDAO bookingServiceUsageDetailDao) {
        this.bookingServiceDao = bookingServiceDao;
        this.bookingServiceUsageDetailDao = bookingServiceUsageDetailDao;
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

    public List<BookingServiceUsageDetailViewModel> createBatchBookingService(List<BookingServiceCreateModel> models) {
        List<BookingService> entities = BookingServiceCreateModel.toListEntity(models);
        List<Integer> newIds = bookingServiceDao.insertBatchBookingService(entities, BookingServiceStatus.REQUESTED);
        if (newIds != null && !newIds.isEmpty()) {
            return bookingServiceUsageDetailDao.findByIds(newIds);
        }
        return Collections.emptyList();
    }

    public List<BookingServiceUsageDetailViewModel> getByBookingIdExceptBookingServiceIds(int bookingId, List<BookingServiceUsageDetailViewModel> models) {
        List<Integer> ids = models.stream().map(BookingServiceUsageDetailViewModel::getBookingServiceId).collect(Collectors.toList());
        return bookingServiceUsageDetailDao.findByBookingIdExceptBookingServiceIds(bookingId, ids);
    }

    public List<BookingServiceUsageDetailViewModel> getByBookingId(int bookingId) {
        return bookingServiceUsageDetailDao.findByBookingId(bookingId);
    }

    public Boolean updateBookingServiceStatusToCompleted(int bookingServiceId) {
        return bookingServiceDao.updateBookingServiceStatus(bookingServiceId, BookingServiceStatus.COMPLETED) > 0;
    }

}
