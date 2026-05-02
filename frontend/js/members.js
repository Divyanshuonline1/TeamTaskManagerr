checkAuth();

const user = getUser();

loadProjects();
loadUsers();

if (user.role !== "ADMIN") {
    const form = document.querySelector("form");
    if (form) form.style.display = "none";
}

async function loadProjects() {
    try {
        const projects = await apiRequest("/projects");

        let html =
            `<option value="">Select Project</option>`;

        projects.forEach(project => {
            html += `
                <option value="${project.id}">
                    ${project.name}
                </option>
            `;
        });

        document.getElementById("projectSelect").innerHTML =
            html;

    } catch (error) {
        console.error(error);
    }
}

async function loadUsers() {
    try {
        const users = await apiRequest("/users");

        let dropdown =
            `<option value="">Select Member</option>`;

        let cards = "";

        users.forEach(u => {

            dropdown += `
                <option value="${u.id}">
                    ${u.name} (${u.role})
                </option>
            `;

            cards += `
                <div class="project-card">
                    <h3>${u.name}</h3>
                    <p>${u.email}</p>
                    <p>
                        <b>Role:</b> ${u.role}
                    </p>

                    <button
                        style="
                            margin-top:15px;
                            width:100%;
                            padding:10px;
                            border:none;
                            border-radius:10px;
                            background:#2563eb;
                            color:white;
                            cursor:pointer;
                            font-weight:600;
                        "
                        onclick="viewReport(${u.id})"
                    >
                        View Report
                    </button>
                </div>
            `;
        });

        document.getElementById("userSelect").innerHTML =
            dropdown;

        document.getElementById("allUsers").innerHTML =
            cards;

    } catch (error) {
        console.error(error);
    }
}

async function addMember(event) {
    event.preventDefault();

    if (user.role !== "ADMIN") {
        alert("Only Admin can add members");
        return;
    }

    const projectId =
        document.getElementById("projectSelect").value;

    const userId =
        document.getElementById("userSelect").value;

    if (!projectId || !userId) {
        alert("Select project and member");
        return;
    }

    try {
        const message = await apiRequest(
            `/projects/${projectId}/members`,
            "POST",
            {
                userId: Number(userId)
            }
        );

        alert(message);

    } catch (error) {
        alert(error.message);
    }
}

async function loadProjectMembers() {

    const projectId =
        document.getElementById("projectSelect").value;

    if (!projectId) {
        alert("Select project");
        return;
    }

    try {
        const members = await apiRequest(
            `/projects/${projectId}/members`
        );

        let html = `<h2>Project Members</h2><br>`;

        members.forEach(member => {
            html += `
                <div class="project-card">
                    <h3>${member}</h3>
                </div>
            `;
        });

        document.getElementById("memberList").innerHTML =
            html;

    } catch (error) {
        alert(error.message);
    }
}

function viewReport(id) {
    window.location.href =
        `member-report.html?id=${id}`;
}