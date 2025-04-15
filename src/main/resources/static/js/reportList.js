document.addEventListener('DOMContentLoaded', function () {
    const createBtn = document.getElementById('createReportBtn');
    const reportsTableBody = document.querySelector('#reportsTable tbody');

    function renderReports(reports) {
        reportsTableBody.innerHTML = '';
        reports.forEach(report => {
            // Определяем цвет фона для статуса
            let statusClass = '';
            if (report.status === 'CREATED') statusClass = 'status-created';
            else if (report.status === 'READY') statusClass = 'status-ready';
            else if (report.status === 'ERROR') statusClass = 'status-error';

            // Только количество продуктов
            let productsCount = Array.isArray(report.products) ? report.products.length : '-';

            const tr = document.createElement('tr');
            tr.classList.add('report-row');
            tr.setAttribute('data-id', report.id);
            tr.innerHTML = `
                <td>${report.id}</td>
                <td class="${statusClass}">${report.status}</td>
                <td>${report.usersCount ?? '-'}</td>
                <td>${productsCount} шт.</td>
                <td>${report.userCountTimeMs ?? '-'}</td>
                <td>${report.productListTimeMs ?? '-'}</td>
                <td>${report.totalReportTimeMs ?? '-'}</td>
                <td><button class="delete-btn" data-id="${report.id}" title="Удалить"></button></td>
            `;
            reportsTableBody.appendChild(tr);
        });
    }

    function fetchReports() {
        fetch('/api/reports')
            .then(res => res.json())
            .then(renderReports);
    }

    createBtn.addEventListener('click', function () {
        fetch('/api/reports', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(res => res.json())
            .then(id => {
                fetchReports();
            });
    });

    reportsTableBody.addEventListener('click', function (e) {
        // Переход на страницу отчета по клику на строку (кроме кнопки удалить)
        const tr = e.target.closest('tr.report-row');
        if (tr && !e.target.classList.contains('delete-btn')) {
            const id = tr.getAttribute('data-id');
            window.location.href = `/reports/${id}`;
            return;
        }
        if (e.target.classList.contains('delete-btn')) {
            const id = e.target.getAttribute('data-id');
            fetch(`/api/reports/${id}`, {
                method: 'DELETE',
            })
                .then(() => fetchReports());
        }
    });

    fetchReports();
});
