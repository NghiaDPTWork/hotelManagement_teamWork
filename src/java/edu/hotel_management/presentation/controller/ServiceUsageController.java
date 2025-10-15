package edu.hotel_management.presentation.controller;

import edu.hotel_management.application.service.BookingService;
import edu.hotel_management.domain.dto.booking.BookingDetailViewModel;
import edu.hotel_management.domain.dto.booking_service.BookingServiceUsageDetailViewModel;
import edu.hotel_management.infrastructure.persistence.dao.*;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.Page;
import edu.hotel_management.presentation.constants.Path;
import edu.hotel_management.presentation.constants.RequestAttribute;
import edu.hotel_management.presentation.constants.SessionAttribute;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "ServiceUsageController", urlPatterns = {"/service-staff/service-usage-detail"})
public class ServiceUsageController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;

    @Override
    public void init() {
        BookingDAO bookingDao;
        BookingDetailDAO bookingDetailDao;
        DataSource ds = DataSourceProvider.getDataSource();
        bookingDao = new BookingDAO(ds);
        bookingDetailDao = new BookingDetailDAO(ds);
        this.bookingService = new BookingService(bookingDao, bookingDetailDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            BookingDetailViewModel bookingDetailViewModel = bookingService.getCheckInBookingDetailById(bookingId);
            if (bookingDetailViewModel == null) {
                throw new IllegalArgumentException("Booking does not exist");
            }
            List<BookingServiceUsageDetailViewModel> newBookingServiceUsageModels =
                    (List<BookingServiceUsageDetailViewModel>) session.getAttribute(SessionAttribute.LIST_NEW_BOOKING_SERVICE_USAGE);
            if (newBookingServiceUsageModels == null) {
                /// ??????????
                response.sendRedirect(request.getContextPath() + Path.SERVICE_STAFF_DASHBOARD_PATH);
                return;
            }
            List<BookingServiceUsageDetailViewModel> oldBookingServiceUsageModels =
                    (List<BookingServiceUsageDetailViewModel>) session.getAttribute(SessionAttribute.LIST_OLD_BOOKING_SERVICE_USAGE);
            request.setAttribute(RequestAttribute.CHECK_IN_BOOKING_DETAILS, bookingDetailViewModel);
            request.setAttribute(RequestAttribute.LIST_NEW_BOOKING_SERVICE_USAGE, newBookingServiceUsageModels);
            request.setAttribute(RequestAttribute.LIST_OLD_BOOKING_SERVICE_USAGE, oldBookingServiceUsageModels);
            // Delete it from the session to avoid reusing incorrect data display and decrease memory usage of the session
            session.removeAttribute(SessionAttribute.LIST_NEW_BOOKING_SERVICE_USAGE);
            session.removeAttribute(SessionAttribute.LIST_OLD_BOOKING_SERVICE_USAGE);
            request.getRequestDispatcher(Page.SERVICE_USAGE_DETAIL_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bookingId");
        }catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}
