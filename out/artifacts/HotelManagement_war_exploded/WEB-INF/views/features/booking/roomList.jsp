<%--
    Document    : bookingDashboard
    Created on  : Oct 5, 2025, 8:24:45 PM
    Author      : TR_NGHIA
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="room-item">
    <div class="room-image-col">
        <span class="room-badge">Available</span>
        <%-- Chỉ hiển thị một hình ảnh duy nhất --%>
        <img src="${pageContext.request.contextPath}/${param.roomImage1}" class="room-single-image d-block w-100 h-100" alt="${param.roomTypeName}">
    </div>
    <div class="room-details-col">
        <div class="room-header">
            <h3 class="room-type-name">${param.roomTypeName}</h3>
            <a href="#" class="btn-room-details">Room Details</a>
        </div>
        <div class="rate-options-list">
            <div class="rate-option-item">
                <div class="rate-info">
                    <h6>Member Flexible Rate <i class="bi bi-info-circle-fill" data-bs-toggle="tooltip" title="Special rate for loyalty program members"></i></h6>
                    <a href="#" class="rate-link">Rate Details</a>
                </div>
                <div class="rate-price">
                    <div class="price-details">
                        <span class="price-value">${param.memberPrice}</span>
                        <span class="price-per-night">/ NIGHT</span>
                    </div>
                    <button class="btn btn-select">Select</button>
                </div>
            </div>
            <div class="rate-option-item">
                <div class="rate-info">
                    <h6>Flexible Rate</h6>
                    <a href="#" class="rate-link">Rate Details</a>
                </div>
                <div class="rate-price">
                    <div class="price-details">
                        <span class="price-value">${param.flexiblePrice}</span>
                        <span class="price-per-night">/ NIGHT</span>
                    </div>
                    <button class="btn btn-select">Select</button>
                </div>
            </div>
        </div>
    </div>
</div>