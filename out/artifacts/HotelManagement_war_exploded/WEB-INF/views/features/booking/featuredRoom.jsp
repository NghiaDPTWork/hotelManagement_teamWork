<%--
    Document    : featuredRoom
    Created on  : Oct 4, 2025, 11:55:00 PM
    Author      : TR_NGHIA
--%>

<%-- B??C 1: Import các l?p Java c?n thi?t --%>
<%@ page import="java.util.List" %>
<%@ page import="edu.hotel_management.presentation.dto.room.RoomPresentationModel" %>
<%@ page import="edu.hotel_management.presentation.constants.IConstant" %>

<section id="rooms" class="section-padding bg-light">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-5 scroll-animate">
            <div>
                <h2 class="section-title">Featured Rooms</h2>
                <p class="text-muted">Discover rooms designed for your comfort and delight.</p>
            </div>
            <a href="${pageContext.request.contextPath}/ViewRoomBookingController" class="btn btn-outline-dark">View All</a>
        </div>

        <div class="row g-4 gy-5">
            <%-- B??C 2: L?y danh sách phòng t? request attribute --%>
            <%
                List<RoomPresentationModel> featuredRoomsList = (List<RoomPresentationModel>) request.getAttribute("featuredRoomsList");

                if (featuredRoomsList != null && !featuredRoomsList.isEmpty()) {
                    int index = 0;
                    for (RoomPresentationModel room : featuredRoomsList) {
                        index++;
            %>
            
            <div class="col-lg-4 col-md-6 scroll-animate" style="animation-delay: <%= index * 0.2 %>s;">
                <div class="card card-hover h-100 room-card">
                    
                    <img src="${pageContext.request.contextPath}/public/img/room-<%= index %>.jpg" class="card-img-top room-image" alt="<%= room.getTypeName() %>">
                    
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title"><%= room.getTypeName() %></h5>
                        
                        <p class="card-text text-muted small">A beautifully designed room offering comfort and style. Perfect for your stay.</p>
                        
                        <ul class="room-amenities-list">
                            <li><i class="bi bi-people-fill"></i> Capacity: <%= room.getCapacityInfo() %></li>
                            <li><i class="bi bi-check-circle-fill"></i> Status: <%= room.getStatus() %></li>
                        </ul>
                        
                        <div class="mt-auto d-flex justify-content-between align-items-center">
                            <p class="room-price mb-0"><%= room.getFormattedPrice() %> <span class="fs-6 text-muted fw-normal">/ night</span></p>
                            
                            <button type="button" class="btn btn-warning view-detail-btn" data-bs-toggle="modal" data-bs-target="#roomDetailModal"
                                    data-room-id="<%= index %>"
                                    data-room-name="<%= room.getTypeName() %>"
                                    data-room-price="<%= room.getFormattedPrice() %>"
                                    data-room-image="${pageContext.request.contextPath}/public/img/room-<%= index %>.jpg">
                                View Detail
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <%
                    } 
                } else {
            %>

            <%-- Hi?n th? thông báo n?u không có phòng nào --%>
            <div class="col-12">
                <p class="text-center text-muted">No featured rooms available at the moment.</p>
            </div>

            <%
                } // K?t thúc kh?i if-else
            %>
        </div>
    </div>
</section>