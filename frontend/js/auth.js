async function registerUser(event) {
    event.preventDefault();

    const data = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        await apiRequest(
            "/auth/register",
            "POST",
            data
        );

        alert("Registration successful");
        window.location.href = "login.html";

    } catch (error) {
        alert(error.message);
    }
}

async function loginUser(event) {
    event.preventDefault();

    const data = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        const result = await apiRequest(
            "/auth/login",
            "POST",
            data
        );

        saveToken(result.token);
        saveUser(result.user);

        alert("Login successful");

        window.location.href = "dashboard.html";

    } catch (error) {
        alert(error.message);
    }
}