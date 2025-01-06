document.addEventListener("DOMContentLoaded", function () {
    fetch("/index", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Unauthorized access");
        }
        return response.text();
    })
    .then(data => {
        console.log("Page content:", data);
    })
    .catch(error => {
        console.error("Access failed:", error);
        window.location.href = "/login";
    });
});