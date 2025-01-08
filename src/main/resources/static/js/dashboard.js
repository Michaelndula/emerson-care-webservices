document.addEventListener("DOMContentLoaded", async () => {
    const token = sessionStorage.getItem("token");

    if (!token) {
        alert("Unauthorized access. Redirecting to login.");
        window.location.href = "/login";
        return;
    }

    try {
        const response = await fetch("/dashboard", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        });

        if (response.ok) {
            document.body.innerHTML = `<h1>Welcome to the Dashboard</h1>`;
        } else {
            alert("Session expired or unauthorized. Redirecting to login.");
            window.location.href = "/login";
        }
    } catch (error) {
        console.error("Error fetching dashboard data:", error);
        alert("An error occurred. Please try again later.");
    }
});