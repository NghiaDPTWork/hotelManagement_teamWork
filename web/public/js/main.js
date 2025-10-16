// Hotel Misauka - Main JavaScript

document.addEventListener('DOMContentLoaded', function () {

    //========== 1. NAVBAR SCROLL EFFECT ==========
    const navbar = document.querySelector('.navbar');
    // Chỉ áp dụng hiệu ứng cuộn nếu không phải trang có navbar nền trắng sẵn
    if (navbar && !document.body.classList.contains('page-solid-navbar')) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                navbar.classList.add('scrolled');
            } else {
                navbar.classList.remove('scrolled');
            }
        });
    }

    //========== 2. BOOKING FORM DATE LOGIC ==========
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
                if (departureDateInput.value && new Date(departureDateInput.value) < arrivalDate) {
                    departureDateInput.value = '';
                }
            }
        });
    }

    //========== 3. SCROLL ANIMATION ==========
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
    
    //========== 4. FORM VALIDATION (CHO CẢ REGISTER VÀ BOOKING) ==========
    const formsToValidate = document.querySelectorAll('form[novalidate]');
    formsToValidate.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!this.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            this.classList.add('was-validated');
        });
    });

    //========== 5. BACK TO TOP BUTTON ==========
    const backToTopBtn = document.getElementById('backToTop');
    if (backToTopBtn) {
        window.addEventListener('scroll', function () {
            if (window.scrollY > 300) {
                backToTopBtn.style.display = 'block';
            } else {
                backToTopBtn.style.display = 'none';
            }
        });
        backToTopBtn.addEventListener('click', function () {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }

    //========== 6. RATE TAB SWITCHING ==========
    const rateTabs = document.querySelectorAll('.rate-tab');
    rateTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // Remove active class from all tabs
            rateTabs.forEach(t => t.classList.remove('active'));
            // Add active class to clicked tab
            this.classList.add('active');
        });
    });

    //========== 7. ROOM CAROUSEL AUTO-PAUSE ON HOVER ==========
    const roomCarousels = document.querySelectorAll('.room-carousel');
    roomCarousels.forEach(carousel => {
        carousel.addEventListener('mouseenter', function() {
            const bsCarousel = bootstrap.Carousel.getInstance(this);
            if (bsCarousel) {
                bsCarousel.pause();
            }
        });
        
        carousel.addEventListener('mouseleave', function() {
            const bsCarousel = bootstrap.Carousel.getInstance(this);
            if (bsCarousel) {
                bsCarousel.cycle();
            }
        });
    });

    //========== 8. SELECT BUTTON FUNCTIONALITY ==========
    const selectButtons = document.querySelectorAll('.btn-select-modern');
    selectButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Add loading state
            const originalText = this.textContent;
            this.textContent = 'Loading...';
            this.disabled = true;
            
            // Simulate booking process
            setTimeout(() => {
                this.textContent = '✓ Selected';
                this.style.background = '#10b981';
                
                // Reset after 2 seconds
                setTimeout(() => {
                    this.textContent = originalText;
                    this.style.background = '';
                    this.disabled = false;
                }, 2000);
            }, 1000);
        });
    });

    //========== 9. STICKY BOOKING SUMMARY BAR ==========
    const bookingSummaryBar = document.querySelector('.booking-summary-bar');
    if (bookingSummaryBar) {
        let lastScrollTop = 0;
        window.addEventListener('scroll', function() {
            let scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            
            if (scrollTop > 200) {
                bookingSummaryBar.style.boxShadow = '0 4px 12px rgba(0,0,0,0.1)';
            } else {
                bookingSummaryBar.style.boxShadow = '0 2px 4px rgba(0,0,0,0.05)';
            }
            
            lastScrollTop = scrollTop;
        });
    }

    //========== 10. CURRENCY SELECTOR ==========
    const currencySelect = document.querySelector('.currency-select');
    if (currencySelect) {
        currencySelect.addEventListener('change', function() {
            const selectedCurrency = this.value;
            const priceElements = document.querySelectorAll('.price-value');
            
            // This would typically make an API call to convert currencies
            // For now, we'll just update the display
            console.log('Currency changed to:', selectedCurrency);
            
            // You can add currency conversion logic here
        });
    }

    //========== 11. FILTER BUTTONS FUNCTIONALITY ==========
    const filterButtons = document.querySelectorAll('.btn-filter-outline');
    filterButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Toggle active state
            this.classList.toggle('active');
            
            // You can add actual filtering logic here
            console.log('Filter clicked:', this.textContent.trim());
        });
    });

    //========== 12. ROOM DETAILS LINK ==========
    const roomDetailsLinks = document.querySelectorAll('.room-details-link');
    roomDetailsLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            // Open modal or navigate to room details page
            console.log('Room details clicked');
            // You can add modal opening logic or page navigation here
        });
    });

    //========== 13. RATE DETAILS LINK ==========
    const rateDetailsLinks = document.querySelectorAll('.rate-details-link');
    rateDetailsLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            // Open modal with rate details
            console.log('Rate details clicked');
            // You can add modal opening logic here
        });
    });

    //========== 14. SHOW WITH TAXES CHECKBOX ==========
    const taxCheckbox = document.querySelector('.tax-checkbox input');
    if (taxCheckbox) {
        taxCheckbox.addEventListener('change', function() {
            const priceElements = document.querySelectorAll('.price-value');
            
            if (this.checked) {
                // Add taxes to prices (example: 10% tax)
                priceElements.forEach(price => {
                    const currentPrice = parseFloat(price.textContent.replace(/,/g, ''));
                    const priceWithTax = currentPrice * 1.1;
                    price.textContent = priceWithTax.toLocaleString('vi-VN');
                });
            } else {
                // Remove taxes from prices
                priceElements.forEach(price => {
                    const currentPrice = parseFloat(price.textContent.replace(/,/g, ''));
                    const priceWithoutTax = currentPrice / 1.1;
                    price.textContent = priceWithoutTax.toLocaleString('vi-VN');
                });
            }
        });
    }

    //========== 15. EDIT BUTTON FUNCTIONALITY ==========
    const editButtons = document.querySelectorAll('.edit-btn-new');
    editButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            // Navigate back to booking form or open edit modal
            console.log('Edit booking clicked');
            // window.location.href = 'index.jsp#booking';
        });
    });

    //========== 16. SMOOTH SCROLL FOR ANCHOR LINKS ==========
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            const href = this.getAttribute('href');
            if (href !== '#' && href !== '') {
                e.preventDefault();
                const target = document.querySelector(href);
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            }
        });
    });

    //========== 17. INITIALIZE BOOTSTRAP TOOLTIPS ==========
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    //========== 18. ROOM CARD HOVER EFFECTS ==========
    const roomCards = document.querySelectorAll('.room-card-modern');
    roomCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });

    //========== 19. LAZY LOADING IMAGES ==========
    if ('IntersectionObserver' in window) {
        const imageObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const img = entry.target;
                    img.src = img.dataset.src || img.src;
                    img.classList.add('loaded');
                    observer.unobserve(img);
                }
            });
        });

        const lazyImages = document.querySelectorAll('img[data-src]');
        lazyImages.forEach(img => imageObserver.observe(img));
    }

    //========== 20. CONSOLE LOG FOR DEBUGGING ==========
    console.log('Hotel Misauka - Booking Dashboard Initialized');
    console.log('Total rooms displayed:', document.querySelectorAll('.room-card-modern').length);
});