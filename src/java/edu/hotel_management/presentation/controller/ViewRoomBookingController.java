package edu.hotel_management.infrastructure.controller;

import edu.hotel_management.application.service.RoomService;
import edu.hotel_management.infrastructure.persistence.dao.BookingDAO;
import edu.hotel_management.infrastructure.persistence.dao.RoomDAO;
import edu.hotel_management.infrastructure.persistence.provider.DataSourceProvider;
import edu.hotel_management.presentation.constants.IConstant;
import edu.hotel_management.presentation.dto.room.RoomPresentationModel;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TR_NGHIA
 */
@WebServlet(name = "ViewRoomBookingController", urlPatterns = {"/" + IConstant.ACTION_VIEW_ROOM_BOOKING})
public class ViewRoomBookingController extends HttpServlet {

    RoomService roomService;

    @Override
    public void init() throws ServletException {
        RoomDAO roomDAO = new RoomDAO(DataSourceProvider.getDataSource());
        BookingDAO bookingDAO = new BookingDAO(DataSourceProvider.getDataSource());
        this.roomService = new RoomService(roomDAO, bookingDAO);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            // =========================================================================
            // BƯỚC 1: LẤY CÁC THAM SỐ TÌM KIẾM TỪ FORM
            // =========================================================================
            String arrivalDate = request.getParameter("arrivalDate");
            String departureDate = request.getParameter("departureDate");
            String roomType = request.getParameter("roomType");
            String adultsStr = request.getParameter("adults");
            String childrenStr = request.getParameter("children");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Chuyển đổi sang kiểu số (có thể validate kỹ hơn nếu cần)
            int adults = (adultsStr != null && !adultsStr.isEmpty()) ? Integer.parseInt(adultsStr) : 0;
            int children = (childrenStr != null && !childrenStr.isEmpty()) ? Integer.parseInt(childrenStr) : 0;
            int totalGuests = adults + children;

            if (arrivalDate == null || arrivalDate.isEmpty()) {
                arrivalDate = LocalDate.now().format(dtf);
            }

            if (departureDate == null || departureDate.isEmpty()) {
                departureDate = LocalDate.now().plusDays(6).format(dtf);
            }
            // =========================================================================
            // BƯỚC 2: KHỞI TẠO SERVICE VÀ TRUY VẤN DỮ LIỆU
            // =========================================================================
            List<RoomPresentationModel> finalAvailableRooms;

            List<RoomPresentationModel> availableRoomsByDate = roomService.findAvailableRooms(arrivalDate, departureDate);

            if (roomType != null && !roomType.isEmpty()) {
                finalAvailableRooms = availableRoomsByDate.stream()
                        .filter(room -> room.getTypeName().equalsIgnoreCase(roomType))
                        .collect(Collectors.toList());
            } else {
                finalAvailableRooms = availableRoomsByDate;
            }

            // =========================================================================
            // BƯỚC 3: GỬI DỮ LIỆU TỚI JSP
            // =========================================================================
            // Gửi danh sách phòng tìm được
            request.setAttribute("AVALABLEROOM", finalAvailableRooms);

            // Gửi lại các thông tin tìm kiếm để hiển thị trên UI
            request.setAttribute("arrivalDate", arrivalDate);
            request.setAttribute("departureDate", departureDate);
            request.setAttribute("selectedRoomType", roomType);
            request.setAttribute("adults", adults);
            request.setAttribute("children", children);

            // =========================================================================
            // BƯỚC 4: CHUYỂN HƯỚNG TỚI TRANG VIEW
            // =========================================================================
            RequestDispatcher rd = request.getRequestDispatcher(IConstant.PAGE_ROOM_BOOKING);
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while searching for rooms.");
            RequestDispatcher rd = request.getRequestDispatcher(IConstant.PAGE_ERROR);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller to handle viewing and searching for available rooms.";
    }
}
