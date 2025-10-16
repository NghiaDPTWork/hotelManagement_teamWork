<%--
    Document    : bookingDashboard
    Created on  : Oct 5, 2025, 8:24:45 PM
    Author      : TR_NGHIA
--%>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Select a Room and Rate - Hotel Misauka</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;500;600;700&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="../../../public/css/style.css"> 
    </head>
    <body class="booking-dashboard"> <%-- Chỉ cần class booking-dashboard --%>

        <%-- Top Navbar của Sheraton --%>
        <nav class="top-navbar-sheraton d-none d-md-block"> <%-- Ẩn trên màn hình nhỏ --%>
            <div class="container d-flex justify-content-end align-items-center">
                <a href="#" class="info-link">
                    <i class="bi bi-question-circle me-1"></i>Help
                </a>
                <a href="#" class="info-link">
                    <i class="bi bi-globe me-1"></i>My Trips
                </a>
                <a href="#" class="info-link">
                    <i class="bi bi-person-circle me-1"></i>Sign In Or Join
                </a>
                <img src="../../../public/img/marriott-bonvoy-logo.svg" alt="Marriott Bonvoy" class="bonvoy-logo ms-3">
            </div>
        </nav>

        <%-- Main Navbar của Sheraton (với thông tin khách sạn) --%>
        <nav class="main-navbar-sheraton navbar navbar-expand-lg">
            <div class="container d-flex align-items-center">
                <a class="navbar-brand me-4" href="#">
                    <img src="../../../public/img/sheraton-logo-white.svg" alt="Sheraton">
                </a>
                <div class="hotel-name-info d-none d-lg-block me-auto"> <%-- Ẩn trên màn hình nhỏ hơn lg --%>
                    <h2>Sheraton Saigon Grand Opera Hotel</h2>
                    <a href="#" class="hotel-details-link">Hotel Details</a>
                </div>

                <div class="contact-info ms-auto">
                    <span><i class="bi bi-geo-alt-fill"></i>88 Dong Khoi, Saigon Ward, Ho Chi Minh City, Vietnam</span>
                    <span><i class="bi bi-telephone-fill"></i>+84 2838272828</span>
                    <span><i class="bi bi-star-fill text-warning"></i>4.5 (1134 reviews)</span>
                </div>
            </div>
        </nav>

        <main class="main-content">
            <%-- Booking Summary Bar (Fixed/Sticky) --%>
            <nav class="navbar-custom">
                <div class="container d-flex justify-content-between align-items-center flex-wrap">
                    <div class="d-flex align-items-center flex-wrap">
                        <div class="summary-item">
                            <span class="summary-label">STAY DATES (1 NIGHT)</span>
                            <span class="summary-value">Thu, Oct 16 - Fri, Oct 17</span>
                        </div>
                        <div class="summary-item">
                            <span class="summary-label">ROOMS & GUESTS</span>
                            <span class="summary-value">1 Room, 1 Guest</span>
                        </div>
                        <div class="summary-item">
                            <span class="summary-label">SPECIAL RATES</span>
                            <span class="summary-value">Lowest Regular Rate</span>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="usePoints">
                            <label class="form-check-label" for="usePoints">Use Points/Awards</label>
                        </div>
                    </div>
                    <button class="btn btn-edit mt-2 mt-md-0">
                        <i class="bi bi-pencil me-2"></i>Edit
                    </button>
                </div>
            </nav>

            <div class="container">
                <section class="page-title-section">
                    <h1 class="page-title">Select a Room and Rate</h1>
                </section>

                <%-- Rate Tabs --%>
                <section class="rate-tabs-section">
                    <button class="rate-tab active">
                        <span class="tab-label">Standard Rates</span>
                        <span class="tab-price">From 5,985,000 VND/Night</span>
                    </button>
                    <button class="rate-tab">
                        <span class="tab-label">Deals and Packages</span>
                        <span class="tab-price">From 6,490,000 VND/Night</span>
                    </button>
                </section>

                <%-- Filter Bar --%>
                <section class="filter-bar">
                    <button class="btn-filter active"><i class="bi bi-sliders me-2"></i>All Filters</button>
                    <div class="dropdown">
                        <button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Accessibility
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Accessible Features</a></li>
                            <li><a class="dropdown-item" href="#">Roll-in Shower</a></li>
                        </ul>
                    </div>
                    <div class="dropdown">
                        <button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Accommodation Type
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Guest Room</a></li>
                            <li><a class="dropdown-item" href="#">Suite</a></li>
                        </ul>
                    </div>
                    <div class="dropdown">
                        <button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Bed Type
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">1 King Bed</a></li>
                            <li><a class="dropdown-item" href="#">2 Twin/Single Beds</a></li>
                        </ul>
                    </div>
                    <div class="dropdown">
                        <button class="dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            View
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">City View</a></li>
                            <li><a class="dropdown-item" href="#">Ocean View</a></li>
                        </ul>
                    </div>
                </section>

                <%-- Room Count Header --%>
                <div class="room-count-header">
                    <span class="room-count-info"><strong>3 Room Types Available</strong></span>
                    <div class="options-right">
                        <label class="tax-checkbox">
                            <input class="form-check-input" type="checkbox" value="" id="showTaxes">
                            Show with taxes and fees
                        </label>
                        <select class="form-select currency-select" aria-label="Currency select">
                            <option selected value="VND">VND</option>
                            <option value="USD">USD</option>
                        </select>
                    </div>
                </div>

                <%-- Room List Container --%>
                <div class="room-list-container">

                    <%-- Nhúng Phòng 1 và truyền dữ liệu --%>
                    <jsp:include page="../features/booking/roomList.jsp">
                        <jsp:param name="roomId" value="1"/>
                        <jsp:param name="roomImage1" value="public/img/room-1.jpg"/>
                        <jsp:param name="roomImage2" value="public/img/fea-1.jpg"/>
                        <jsp:param name="roomTypeName" value="1 King Bed, Guest Room"/>
                        <jsp:param name="memberPrice" value="5,985,000 VND"/>
                        <jsp:param name="flexiblePrice" value="6,300,000 VND"/>
                    </jsp:include>

                    <%-- Nhúng Phòng 2 và truyền dữ liệu --%>
                    <jsp:include page="../features/booking/roomList.jsp">
                        <jsp:param name="roomId" value="2"/>
                        <jsp:param name="roomImage1" value="public/img/room-2.jpg"/>
                        <jsp:param name="roomImage2" value="public/img/fea-3.jpg"/>
                        <jsp:param name="roomTypeName" value="2 Twin/Single Beds, Guest Room"/>
                        <jsp:param name="memberPrice" value="5,985,000 VND"/>
                        <jsp:param name="flexiblePrice" value="6,300,000 VND"/>
                    </jsp:include>

                    <%-- Nhúng Phòng 3 và truyền dữ liệu --%>
                    <jsp:include page="../features/booking/roomList.jsp">
                        <jsp:param name="roomId" value="3"/>
                        <jsp:param name="roomImage1" value="public/img/room-3.jpg"/>
                        <jsp:param name="roomImage2" value="public/img/fea-2.png"/>
                        <jsp:param name="roomTypeName" value="Club Deluxe Room, 1 King"/>
                        <jsp:param name="memberPrice" value="7,250,000 VND"/>
                        <jsp:param name="flexiblePrice" value="7,800,000 VND"/>
                    </jsp:include>

                </div> <%-- End room-list-container --%>

            </div> <%-- End container --%>
        </main>

        <%@ include file="../features/login/login.jsp" %>
        <%@ include file="../shared/footer.jsp" %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="../../../public/js/main.js"></script>
        <script>
            // Kích hoạt tooltip của Bootstrap (nếu bạn muốn dùng tooltip cho icon info)
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl)
            })
        </script>
    </body>
</html>