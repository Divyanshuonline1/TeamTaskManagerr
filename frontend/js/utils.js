function showMessage(message, type = "success") {
    alert(message);
}

function saveToken(token) {
    localStorage.setItem(CONFIG.TOKEN_KEY, token);
}

function getToken() {
    return localStorage.getItem(CONFIG.TOKEN_KEY);
}

function removeToken() {
    localStorage.removeItem(CONFIG.TOKEN_KEY);
}

function saveUser(user) {
    localStorage.setItem(
        CONFIG.USER_KEY,
        JSON.stringify(user)
    );
}

function getUser() {
    return JSON.parse(
        localStorage.getItem(CONFIG.USER_KEY)
    );
}

function removeUser() {
    localStorage.removeItem(CONFIG.USER_KEY);
}

function logout() {
    removeToken();
    removeUser();
    window.location.href = "login.html";
}

function applyRoleBasedNavbar() {
    const user = getUser();

    if (!user) return;

    // hide Members menu for MEMBER
    if (user.role === "MEMBER") {
        document.querySelectorAll("nav a").forEach(link => {
            if (
                link.textContent.trim().toLowerCase() === "members"
            ) {
                link.style.display = "none";
            }
        });
    }
}

function checkAuth() {
    const token = getToken();

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    applyRoleBasedNavbar();
}