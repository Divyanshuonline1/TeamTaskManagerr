checkAuth();

const user = getUser();

loadProjects();

if (user.role !== "ADMIN") {
    document.getElementById("projectPanel").style.display = "none";
}

async function createProject(event) {
    event.preventDefault();

    const data = {
        name: name.value,
        description: description.value,
        priority: priority.value,
        riskLevel: riskLevel.value,
        expectedTeamSize: Number(expectedTeamSize.value || 0),
        budget: Number(budget.value || 0),
        startDate: startDate.value,
        deadline: deadline.value,
        notes: notes.value
    };

    try {
        await apiRequest("/projects", "POST", data);

        alert("Project created successfully");

        event.target.reset();

        loadProjects();

    } catch (error) {
        alert(error.message);
    }
}

async function loadProjects() {
    try {
        const projects = await apiRequest("/projects");

        let planning = 0;
        let active = 0;
        let completed = 0;

        let html = "";

        projects.forEach(project => {

            if (project.status === "PLANNING") planning++;
            if (project.status === "ACTIVE") active++;
            if (project.status === "COMPLETED") completed++;

            const today = new Date();
            const deadline = project.deadline
                ? new Date(project.deadline)
                : null;

            let daysLeft = "-";

            if (deadline) {
                const diff =
                    Math.ceil(
                        (deadline - today) /
                        (1000 * 60 * 60 * 24)
                    );

                daysLeft = diff + " days";
            }

            html += `
                <div class="project-card">
                    <h3>${project.name}</h3>

                    <p>${project.description || ""}</p>

                    <p><b>Priority:</b> ${project.priority}</p>
                    <p><b>Risk:</b> ${project.riskLevel}</p>
                    <p><b>Status:</b> ${project.status}</p>
                    <p><b>Members:</b> ${project.totalMembers}</p>
                    <p><b>Tasks:</b> ${project.totalTasks}</p>
                    <p><b>Team Size:</b> ${project.expectedTeamSize || 0}</p>
                    <p><b>Budget:</b> ₹${project.budget || 0}</p>
                    <p><b>Deadline:</b> ${project.deadline || "-"}</p>
                    <p><b>Days Left:</b> ${daysLeft}</p>

                    ${
                        project.notes
                        ? `<p><b>Notes:</b> ${project.notes}</p>`
                        : ``
                    }

                    <div class="progress-wrap">
                        <div class="progress-bar"
                             style="width:${project.progress}%">
                        </div>
                    </div>

                    <p><b>Progress:</b> ${project.progress}%</p>

                    <div class="action-row">
                        <button onclick="goMembers()">Members</button>
                        <button onclick="goTasks()">Tasks</button>
                    </div>
                </div>
            `;
        });

        projectList.innerHTML = html;

        totalProjects.innerText = projects.length;
        planningProjects.innerText = planning;
        activeProjects.innerText = active;
        completedProjects.innerText = completed;

    } catch (error) {
        console.error(error);
    }
}

function goMembers() {
    window.location.href = "members.html";
}

function goTasks() {
    window.location.href = "tasks.html";
}