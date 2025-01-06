document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Invalid username or password");
            }
            return response.text();
        })
        .then((token) => {
            console.log("JWT Token:", token);
            localStorage.setItem("jwtToken", token);
            window.location.href = "/index";
        })
        .catch((error) => {
            console.error("Login failed:", error);
            alert("Invalid username or password");
        });
});