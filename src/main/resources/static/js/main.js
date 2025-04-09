document.addEventListener('DOMContentLoaded', function() {
    // Добавление класса к хедеру при скроллинге
    const header = document.querySelector('.main-header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

    // Плавная прокрутка к разделам при клике на ссылки
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });

    // Анимации при скроллинге
    const featureCards = document.querySelectorAll('.feature-card');

    // Функция для проверки, находится ли элемент в видимой области
    function isElementInViewport(el) {
        const rect = el.getBoundingClientRect();
        return (
            rect.top >= 0 &&
            rect.left >= 0 &&
            rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.right <= (window.innerWidth || document.documentElement.clientWidth)
        );
    }

    // Функция для анимации элементов при скролле
    function animateOnScroll() {
        featureCards.forEach(card => {
            if (isElementInViewport(card)) {
                card.classList.add('animate-in');
            }
        });
    }

    // Вызываем функцию при загрузке и скролле
    window.addEventListener('scroll', animateOnScroll);
    animateOnScroll(); // Вызов при загрузке страницы

    // Обработка выхода из системы
    const logoutForm = document.querySelector('form[action="/logout"]');
    if (logoutForm) {
        logoutForm.addEventListener('submit', function(e) {
            const confirmLogout = confirm('Вы уверены, что хотите выйти?');
            if (!confirmLogout) {
                e.preventDefault();
            }
        });
    }

    // Инициализация дропдауна пользователя
    const userDropdown = document.querySelector('.user-dropdown');
    const dropdownContent = document.querySelector('.dropdown-content');

    if (userDropdown && dropdownContent) {
        // Показываем/скрываем дропдаун при клике
        userDropdown.addEventListener('click', function(e) {
            if (e.target.closest('.user-button')) {
                dropdownContent.classList.toggle('show');
                e.stopPropagation();
            }
        });

        // Скрываем дропдаун при клике вне его
        document.addEventListener('click', function(e) {
            if (!e.target.closest('.user-dropdown')) {
                dropdownContent.classList.remove('show');
            }
        });
    }

    // Добавляем анимации CSS для элементов с классом animate-in
    document.head.insertAdjacentHTML('beforeend', `
        <style>
            .animate-in {
                animation: fadeInUp 0.6s ease forwards;
            }
            
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            .feature-card {
                opacity: 0;
            }
            
            .main-header.scrolled {
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                padding: 10px 0;
            }
            
            .dropdown-content.show {
                display: block;
            }
        </style>
    `);
});