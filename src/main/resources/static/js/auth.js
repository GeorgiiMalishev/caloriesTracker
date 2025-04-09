document.addEventListener('DOMContentLoaded', function() {
    // Показать/скрыть пароль
    const togglePasswordButtons = document.querySelectorAll('.toggle-password');

    togglePasswordButtons.forEach(button => {
        button.addEventListener('click', function() {
            const input = this.previousElementSibling;

            if (input.type === 'password') {
                input.type = 'text';
                this.classList.remove('fa-eye-slash');
                this.classList.add('fa-eye');
                this.title = 'Скрыть пароль';
            } else {
                input.type = 'password';
                this.classList.remove('fa-eye');
                this.classList.add('fa-eye-slash');
                this.title = 'Показать пароль';
            }
        });
    });

    // Обработка формы регистрации
    const registerForm = document.getElementById('register-form');

    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm-password').value;

            const errorMessage = document.getElementById('error-message');
            const successMessage = document.getElementById('success-message');

            // Проверка паролей
            if (password !== confirmPassword) {
                errorMessage.textContent = 'Пароли не совпадают!';
                errorMessage.style.display = 'block';
                successMessage.style.display = 'none';
                return;
            }

            // Подготовка данных для отправки
            const userData = {
                username: username,
                email: email,
                password: password
            };

            // Отправка данных на сервер
            fetch('/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(data => {
                            throw new Error(data.message || 'Ошибка при регистрации');
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    // Успешная регистрация
                    errorMessage.style.display = 'none';
                    successMessage.textContent = 'Регистрация прошла успешно! Перенаправление на страницу входа...';
                    successMessage.style.display = 'block';

                    // Перенаправление на страницу входа через 2 секунды
                    setTimeout(() => {
                        window.location.href = '/login';
                    }, 2000);
                })
                .catch(error => {
                    // Ошибка при регистрации
                    errorMessage.textContent = error.message;
                    errorMessage.style.display = 'block';
                    successMessage.style.display = 'none';
                });
        });
    }
});