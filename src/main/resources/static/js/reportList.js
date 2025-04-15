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

            const tr = document.createElement('tr');
            tr.classList.add('report-row');
            tr.setAttribute('data-id', report.id);
            tr.innerHTML = `
                <td>${report.id}</td>
                <td class="${statusClass}">${report.status}</td>
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
            .then(() => {
                fetchReports();
            });
    });

    reportsTableBody.addEventListener('click', function (e) {
        const tr = e.target.closest('tr.report-row');
        if (tr) {
            const id = tr.getAttribute('data-id');
            window.location.href = `/reports/${id}`;
        }
    });

    fetchReports();
});
