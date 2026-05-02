checkAuth();

const user = getUser();

if(user){
    document.getElementById("name").innerText =
        user.name;

    document.getElementById("email").innerText =
        user.email;

    document.getElementById("role").innerText =
        user.role;
}
const initials = name.split(' ').map(w => w[0]).join('').slice(0, 2).toUpperCase();
document.getElementById('avatarInitials').textContent = initials;
