document.addEventListener('DOMContentLoaded', () => {
        const apiUrl = '/api/providers/all';
        const allUsersTableBody = document.querySelector('#all-providers-table tbody');

        // Fetch users from the API
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch Providers');
                }
                return response.json();
            })
            .then(users => {
                // Clear existing rows in the table
                allUsersTableBody.innerHTML = '';

                // Sort users by dateCreated (most recent first)
                users.sort((a, b) => new Date(b.dateCreated) - new Date(a.dateCreated));

                // Populate the table with user data
                users.forEach((user, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td class="cell">${index + 1}</td>
                        <td class="cell">${user.firstName} ${user.middleName || ''} ${user.lastName}</td>
                        <td class="cell">${user.email}</td>
                        <td class="cell">${user.role}</td>
                        <td class="cell">${new Date(user.dateCreated).toLocaleString()}</td>
                        <td class="cell">
                            <a class="btn-sm app-btn-secondary" href="/user/${user.uuid}">View</a>
                        </td>
                    `;
                    allUsersTableBody.appendChild(row);
                });
            })
            .catch(error => {
                console.error('Error fetching Providers:', error);
                allUsersTableBody.innerHTML = `
                    <tr>
                        <td colspan="6" class="text-center">Error loading Providers</td>
                    </tr>
                `;
            });
    });