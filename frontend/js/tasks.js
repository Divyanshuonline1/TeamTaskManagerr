checkAuth();

const user = getUser();

loadProjects();
loadUsers();
loadTasks();

if (user.role !== "ADMIN") {
    document.getElementById("adminPanel").style.display = "none";
}

async function loadProjects() {
    try {
        const projects = await apiRequest("/projects");

        let html =
            `<option value="">Select Project</option>`;

        projects.forEach(p => {
            html += `
                <option value="${p.id}">
                    ${p.name}
                </option>
            `;
        });

        document.getElementById("projectId").innerHTML =
            html;

    } catch (e) {
        console.error(e);
    }
}

async function loadUsers() {
    try {
        const users = await apiRequest("/users");

        let html =
            `<option value="">Assign Member</option>`;

        users.forEach(u => {
            if (u.role === "MEMBER") {
                html += `
                    <option value="${u.id}">
                        ${u.name}
                    </option>
                `;
            }
        });

        document.getElementById("assignedUserId").innerHTML =
            html;

    } catch (e) {
        console.error(e);
    }
}

async function createTask(event) {
    event.preventDefault();

    const data = {
        title: title.value,
        description: description.value,
        projectId: Number(projectId.value),
        assignedUserId: Number(assignedUserId.value),
        category: category.value,
        priority: priority.value,
        estimatedHours: Number(estimatedHours.value || 0),
        dueDate: dueDate.value,
        notes: notes.value
    };

    try {
        await apiRequest("/tasks", "POST", data);

        alert("Task assigned successfully");

        event.target.reset();

        loadTasks();

    } catch (error) {
        alert(error.message);
    }
}

async function updateStatus(id, status) {
    if (!status) return;

    try {
        await apiRequest(
            `/tasks/${id}/status?status=${status}`,
            "PATCH"
        );

        loadTasks();

    } catch (error) {
        alert(error.message);
    }
}

async function loadTasks() {
    try {
        const tasks = await apiRequest("/tasks");

        let pending = 0;
        let progress = 0;
        let completed = 0;

        let html = "";

        tasks.forEach(task => {

            if (task.status === "PENDING") pending++;
            if (task.status === "IN_PROGRESS") progress++;
            if (task.status === "COMPLETED") completed++;

            html += `
                <div class="task-card">
                    <h3>${task.title}</h3>

                    <p>${task.description || ""}</p>

                    <p><b>Project:</b> ${task.projectName}</p>
                    <p><b>Assigned:</b> ${task.assignedTo}</p>
                    <p><b>Category:</b> ${task.category || "-"}</p>
                    <p><b>Hours:</b> ${task.estimatedHours || 0}</p>
                    <p><b>Priority:</b> ${task.priority}</p>
                    <p><b>Status:</b> ${task.status}</p>
                    <p><b>Progress:</b> ${task.progress}%</p>
                    <p><b>Due:</b> ${task.dueDate || "-"}</p>

                    ${
                        task.notes
                        ? `<p><b>Notes:</b> ${task.notes}</p>`
                        : ``
                    }

                    ${
                        task.overdue
                        ? `<p class="overdue">Overdue</p>`
                        : ``
                    }

                    <select onchange="updateStatus(${task.id}, this.value)">
                        <option value="">Change Status</option>
                        <option value="PENDING">PENDING</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="COMPLETED">COMPLETED</option>
                    </select>
                </div>
            `;
        });

        taskList.innerHTML = html;

        totalCount.innerText = tasks.length;
        pendingCount.innerText = pending;
        progressCount.innerText = progress;
        completedCount.innerText = completed;

    } catch (error) {
        console.error(error);
    }
}