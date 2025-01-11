document.addEventListener('DOMContentLoaded', () => {
    const userApiUrl = '/api/users/all';
    const patientApiUrl = '/api/patients/all';
    const providersApiUrl = '/api/providers/all';
    const userStatsFigure = document.getElementById('users-stats-figure');
    const patientStatsFigure = document.getElementById('patients-stats-figure');
    const providersStatsFigure = document.getElementById('providers-stats-figure');

    // Fetch total users
    fetch(userApiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch total users');
            }
            return response.json();
        })
        .then(data => {
            userStatsFigure.textContent = data.length.toLocaleString();
        })
        .catch(error => {
            console.error('Error fetching users:', error);
            userStatsFigure.textContent = 'Error';
        });

    // Fetch total patients
    fetch(patientApiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch total patients');
            }
            return response.json();
        })
        .then(data => {
            patientStatsFigure.textContent = data.length.toLocaleString();
        })
        .catch(error => {
            console.error('Error fetching patients:', error);
            patientStatsFigure.textContent = 'Error';
        });

    // Fetch total providers
    fetch(providersApiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch total providers');
            }
            return response.json();
        })
        .then(data => {
            providersStatsFigure.textContent = data.length.toLocaleString();
        })
        .catch(error => {
            console.error('Error fetching providers:', error);
            providersStatsFigure.textContent = 'Error';
        });
});