document.addEventListener('DOMContentLoaded', function () {

    /*--- 1. Navbar Scroll Effect ---*/
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
    }

    /*--- 2. Booking Form Date Logic ---*/
    const arrivalDateInput = document.getElementById('arrivalDate');
    const departureDateInput = document.getElementById('departureDate');
    if (arrivalDateInput && departureDateInput) {
        const today = new Date().toISOString().split('T')[0];
        arrivalDateInput.setAttribute('min', today);
        arrivalDateInput.addEventListener('change', function () {
            if (this.value) {
                const arrivalDate = new Date(this.value);
                arrivalDate.setDate(arrivalDate.getDate() + 1);
                const nextDay = arrivalDate.toISOString().split('T')[0];
                departureDateInput.setAttribute('min', nextDay);
                if (departureDateInput.value && departureDateInput.value < nextDay) {
                    departureDateInput.value = '';
                }
            }
        });
    }

    /*--- 3. Scroll Animation ---*/
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('is-visible');
            }
        });
    }, {
        threshold: 0.1
    });
    const elementsToAnimate = document.querySelectorAll('.scroll-animate');
    elementsToAnimate.forEach(el => observer.observe(el));

    /*--- 4. Register Form Validation ---*/
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Ngăn form gửi đi để kiểm tra

            let isFormValid = true;

            // Xóa các lỗi cũ
            this.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

            // Lấy các trường input
            const fullName = document.getElementById('signup-fullname');
            const username = document.getElementById('signup-username');
            const email = document.getElementById('signup-email');
            const phone = document.getElementById('signup-phone');
            const password = document.getElementById('signup-password');
            const confirmPassword = document.getElementById('signup-confirm-password');

            // Hàm hỗ trợ kiểm tra lỗi và cập nhật UI
            function validateField(field, condition) {
                if (condition) {
                    field.classList.remove('is-invalid');
                    return true;
                } else {
                    field.classList.add('is-invalid');
                    isFormValid = false;
                    return false;
                }
            }

            // Thực hiện kiểm tra
            validateField(fullName, fullName.value.trim() !== '');
            validateField(username, username.value.length >= 6);
            validateField(email, /^\S+@\S+\.\S+$/.test(email.value));
            validateField(phone, /(0[3|5|7|8|9])+([0-9]{8})\b/.test(phone.value));
            validateField(password, password.value.length >= 8);
            
            // Kiểm tra mật khẩu xác nhận
            if (password.value !== confirmPassword.value || confirmPassword.value === '') {
                confirmPassword.classList.add('is-invalid');
                // Cập nhật thông báo lỗi tương ứng
                const errorDiv = confirmPassword.nextElementSibling;
                if (errorDiv && errorDiv.classList.contains('invalid-feedback')) {
                    errorDiv.textContent = (password.value !== confirmPassword.value) 
                        ? 'Mật khẩu không khớp.' 
                        : 'Vui lòng xác nhận mật khẩu.';
                }
                isFormValid = false;
            } else {
                confirmPassword.classList.remove('is-invalid');
            }

            // Nếu tất cả hợp lệ, gửi form đi
            if (isFormValid) {
                console.log('Register form is valid, submitting...');
                this.submit();
            }
        });
    }


    /*--- 5. Booking Form Submission Validation ---*/
    const bookingForm = document.getElementById('bookingForm');
    if (bookingForm) {
        bookingForm.addEventListener('submit', function (event) {
            // Ngăn chặn việc gửi form ngay lập tức để thực hiện kiểm tra
            event.preventDefault();

            let isFormValid = true;
            
            // Lấy các trường input
            const arrivalDate = document.getElementById('arrivalDate');
            const departureDate = document.getElementById('departureDate');
            const roomType = document.getElementById('roomType');
            const adults = document.getElementById('adults');

            // Xóa lỗi cũ
            const fields = [arrivalDate, departureDate, roomType, adults];
            fields.forEach(field => {
                if(field) field.classList.remove('is-invalid')
            });

            // Kiểm tra từng trường
            if (!arrivalDate || arrivalDate.value === '') {
                arrivalDate.classList.add('is-invalid');
                isFormValid = false;
            }
            if (!departureDate || departureDate.value === '') {
                departureDate.classList.add('is-invalid');
                isFormValid = false;
            }
            if (!roomType || roomType.value === '') {
                roomType.classList.add('is-invalid');
                isFormValid = false;
            }
            if (!adults || adults.value < 1) {
                adults.classList.add('is-invalid');
                isFormValid = false;
            }

            // Nếu form hợp lệ, gửi đi
            if (isFormValid) {
                console.log('Booking form is valid, submitting...');
                this.submit(); // Gửi form đi sau khi đã kiểm tra hợp lệ
            }
        });
    }

    /*--- 6. Room Detail Modal Logic (ĐÃ CẬP NHẬT) ---*/
    const roomDetailModal = document.getElementById('roomDetailModal');
    if (roomDetailModal) {

        // Lắng nghe sự kiện khi modal sắp được hiển thị
        roomDetailModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget; // Nút đã kích hoạt modal

            // Trích xuất thông tin từ các thuộc tính data-* của nút
            const roomId = button.getAttribute('data-room-id');
            const roomName = button.getAttribute('data-room-name');
            const roomPrice = button.getAttribute('data-room-price');
            const roomImage = button.getAttribute('data-room-image');

            // Tìm thẻ card chứa nút để lấy mô tả và tiện nghi
            const roomCardBody = button.closest('.card-body');
            const roomDescription = roomCardBody.querySelector('.card-text').textContent;
            const roomAmenitiesHTML = roomCardBody.querySelector('.room-amenities-list').innerHTML;

            // Cập nhật nội dung của modal
            const modal = this;
            const modalBody = modal.querySelector('.modal-body');

            // Cập nhật nội dung động cho modal
            modal.querySelector('#modalRoomTitle').textContent = roomName;
            modalBody.innerHTML = `
                <div class="row">
                    <div class="col-md-6">
                        <img src="${roomImage}" class="img-fluid rounded" alt="Room Image">
                    </div>
                    <div class="col-md-6">
                        <p class="text-muted">${roomDescription}</p>
                        <h5>Amenities</h5>
                        <ul class="room-amenities-list">
                            ${roomAmenitiesHTML}
                        </ul>
                        <div class="mt-3">
                            <p class="room-price mb-0 fs-4">${roomPrice}</p>
                        </div>
                    </div>
                </div>
            `;
            
            // Gắn ID của phòng vào nút "Book This Room" để sử dụng sau
            modal.querySelector('#bookNowBtn').dataset.roomId = roomId;
        });

        // Xử lý khi nhấn nút "Book This Room" bên trong modal
        const bookNowBtn = document.getElementById('bookNowBtn');
        bookNowBtn.addEventListener('click', function () {
            const roomIdToBook = this.dataset.roomId; // Lấy ID phòng đã lưu

            // 1. Cập nhật loại phòng trong booking form chính
            const roomTypeSelect = document.getElementById('roomType');
            if (roomTypeSelect && roomIdToBook) {
                roomTypeSelect.value = roomIdToBook;
            }

            // 2. Đóng modal
            const modalInstance = bootstrap.Modal.getInstance(roomDetailModal);
            modalInstance.hide();
            
            // 3. Tìm nút submit chính của booking form
            const mainBookingForm = document.getElementById('bookingForm');
            const mainSubmitButton = mainBookingForm.querySelector('button[type="submit"]');

            if (mainSubmitButton) {
                // 4. Kích hoạt sự kiện click trên nút submit chính
                // Hành động này sẽ kích hoạt logic validation (phần 5) mà bạn đã viết.
                mainSubmitButton.click();
            } else {
                // Xử lý trường hợp người dùng chưa đăng nhập, nút submit không tồn tại
                const loginButton = mainBookingForm.querySelector('button[data-bs-target="#loginModal"]');
                if (loginButton) {
                     loginButton.click();
                }
            }
        });
    }
});