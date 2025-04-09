document.addEventListener('DOMContentLoaded', function() {
    // Получение элементов модального окна
    const modal = document.getElementById('addProductModal');
    const addProductBtn = document.getElementById('addProductBtn');
    const closeBtn = document.querySelector('.close');
    const addProductForm = document.getElementById('addProductForm');

    // Открытие модального окна при нажатии на кнопку "Добавить продукт"
    addProductBtn.addEventListener('click', function() {
        modal.style.display = 'block';
    });

    // Закрытие модального окна при нажатии на X
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Закрытие модального окна при клике вне его
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Обработка отправки формы
    addProductForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Сбор данных формы
        const formData = {
            name: document.getElementById('name').value,
            calories: parseFloat(document.getElementById('calories').value),
            proteins: parseFloat(document.getElementById('proteins').value),
            carbs: parseFloat(document.getElementById('carbs').value),
            fats: parseFloat(document.getElementById('fats').value)
        };

        // Получение CSRF токена из скрытого поля
        const csrfToken = document.getElementById('csrf').value;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        // Отправка данных на сервер с CSRF токеном
        // Обратите внимание на обновленный URL для API, основанный на аннотации контроллера
        fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken  // Динамически устанавливаем имя заголовка CSRF
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка сети: ' + response.status);
                }
                return response.json();
            })
            .then(product => {
                // Закрытие модального окна
                modal.style.display = 'none';

                // Добавление нового продукта в таблицу
                addProductToTable(product);

                // Сброс формы
                addProductForm.reset();

            })
            .catch(error => {
                console.error('Ошибка при добавлении продукта:', error);
                alert('Произошла ошибка при добавлении продукта: ' + error.message);
            });
    });

    // Функция для добавления нового продукта в таблицу
    function addProductToTable(product) {
        const tableBody = document.querySelector('tbody');
        const newRow = document.createElement('tr');

        newRow.innerHTML = `
            <td>${product.id || ''}</td>
            <td>${product.name}</td>
            <td>${product.calories} ккал</td>
            <td>${product.proteins} г</td>
            <td>${product.carbs} г</td>
            <td>${product.fats} г</td>
        `;

        tableBody.appendChild(newRow);
    }
});