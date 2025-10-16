package edu.hotel_management.presentation.constants;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author TR_NGHIA
 */

public interface IConstant {

    //========== CÁC HẰNG SỐ CHUNG ==========
    public static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //========== SERVLET & ACTIONS (Dùng cho Front Controller) ==========
    public static final String ACTION_HOME = "HomeController";
    
    
    // ======= LOGIN | REGISTER =======
    public static final String ACTION_LOGIN = "LoginController";
    public static final String ACTION_LOGOUT = "LogoutController";
    public static final String ACTION_REGISTER = "RegisterController";
    
    
    // ======= PROFILE | HISTORY BOOKING =======
    public static final String ACTION_VIEW_PROFILE = "ViewProfileController";
    public static final String ACTION_VIEW_HISTORY_BOOKING = "ViewHistoryBookingController";
    
 
    // ======= SEARCH ROOM | MAKE BOOKING  =======
    public static final String ACTION_VIEW_ROOM_BOOKING = "ViewRoomBookingController";
    public static final String ACTION_BOOKING = "BookingController";
    
    
    // ======= MAIN ACTION  =======
    public static final String LOGIN = "Login";
    public static final String LOGOUT = "logout";
    public static final String REGISTER = "register";
    public static final String BOOKING = "booking";
    public static final String VIEW_PROFILE = "viewProfile";
    public static final String VIEW_HISTORY_ROOM_BOOKING = "viewHistoryRoomBooking";
    public static final String VIEW_ROOM_BOOKING = "viewRoomBooking";
    
    
    
    //========== CÁC LAYOUT CHÍNH ==========
    public static final String PAGE_HOME = "/home.jsp";
    public static final String PAGE_ERROR = "/error.jsp";
    public static final String PAGE_GUEST = ""; 
    public static final String PAGE_ADMIN = "";
    public static final String PAGE_RECEPTIONIST = "";
    public static final String PAGE_MANAGER = "";

    
    //========== CÁC TRANG CHUC NANG CHÍNH ==========
<<<<<<< HEAD
    public static final String PAGE_LOGIN = "/WEB-INF/views/features/login/login.jsp";
    public static final String PAGE_SIGN_UP = "/WEB-INF/views/features/login/sign-up.jsp";
    public static final String PAGE_ROOM_BOOKING = "/WEB-INF/views/layout/bookingDashboard.jsp";
=======
    public static final String PAGE_LOGIN = "/WEB-INF/views/features/auth/login.jsp";
    public static final String PAGE_SIGN_UP = "/WEB-INF/views/features/auth/sign-up.jsp";
    public static final String PAGE_ROOM_BOOKING = "/WEB-INF/views/features/booking/viewRoomsBooking.jsp";
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab

}
