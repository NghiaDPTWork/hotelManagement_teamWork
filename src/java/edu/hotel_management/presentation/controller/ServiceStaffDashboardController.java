/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.hotel_management.presentation.controller;

import edu.hotel_management.application.service.BookingService;
import edu.hotel_management.domain.dto.booking.BookingDetailViewModel;
import edu.hotel_management.infrastructure.persistence.dao.BookingDAO;
import edu.hotel_management.infrastructure.persistence.dao.BookingDetailDAO;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.Page;
import edu.hotel_management.presentation.constants.RequestAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author thuannd.dev
 */
@WebServlet(name = "ServiceStaffDashboardController", urlPatterns = {"/service-staff"})
public class ServiceStaffDashboardController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;


    @Override
    public void init() {
        BookingDAO bookingDAO;
        BookingDetailDAO bookingDetailDAO;
        DataSource ds = DataSourceProvider.getDataSource();
        bookingDAO = new BookingDAO(ds);
        bookingDetailDAO = new BookingDetailDAO(ds);
        this.bookingService = new BookingService(bookingDAO, bookingDetailDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String guestName = request.getParameter("guestName");
//        String roomNumber = request.getParameter("roomNumber");

        List<BookingDetailViewModel> bookings = bookingService.getAllCheckInBookingDetails();

        request.setAttribute(RequestAttribute.ALL_CHECK_IN_BOOKING_DETAILS, bookings);
        request.getRequestDispatcher(Page.SERVICE_STAFF_DASHBOARD_PAGE).forward(request, response);

    }

}
