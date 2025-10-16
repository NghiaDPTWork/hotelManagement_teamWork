<%--
    Document   : home
    Created on : Oct 4, 2025, 7:38:31 PM
    Author     : TR_NGHIA
--%>

<%-- Header  --%>
<%@ include file="./WEB-INF/views/shared/header.jsp" %>

<%-- Alert  --%>
<%@ include file="./WEB-INF/views/shared/alert.jsp" %>

<%-- Navbar  --%>
<%@ include file="./WEB-INF/views/features/auth/navbar.jsp" %>

<<<<<<< HEAD:build/web/home.jsp
<%-- Login Modal --%>
<%@ include file="WEB-INF/views/features/login/login.jsp" %>
=======
<%-- Login Modal (?n) --%>
<%@ include file="WEB-INF/views/features/auth/login.jsp" %>
>>>>>>> ceb12419d611a5f985f89a588c802fe945e0e9ab:out/artifacts/HotelManagement_war_exploded/home.jsp

<%-- Register Modal (?n) --%>
<%@ include file="WEB-INF/views/features/auth/register.jsp" %>

<main>
    <%-- Hero Section --%>
    <%@ include file="./WEB-INF/views/component/home/hero.jsp" %>
    
    <%-- Booking Form Section --%>
    <%@ include file="./WEB-INF/views/features/booking/bookingForm.jsp" %>
    
    <%-- About & Core Features Section --%>
    <%@ include file="./WEB-INF/views/component/home/aboutCoreFeatures.jsp" %>

    <%-- Featured Rooms Section --%>
    <%@ include file="./WEB-INF/views/features/booking/featuredRoom.jsp" %>

    <%-- Dining Section --%>
    <%@ include file="./WEB-INF/views/component/home/dining.jsp" %>
    
    <%-- Testimonials & Blog Section --%>
    <%@ include file="./WEB-INF/views/component/home/testimonialsBlog.jsp" %>
    
    <%-- Map Section --%>
    <%@ include file="./WEB-INF/views/component/home/map.jsp" %>
</main>

<%-- Footer --%>
<%@ include file="./WEB-INF/views/shared/footer.jsp" %>