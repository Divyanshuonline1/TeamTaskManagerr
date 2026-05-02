checkAuth();

loadReport();

async function loadReport() {

    const params =
        new URLSearchParams(window.location.search);

    const id = params.get("id");

    if (!id) {
        alert("User ID missing");
        return;
    }

    try {
        const data =
            await apiRequest(`/users/${id}/report`);

        document.getElementById("name").innerText =
            data.name;

        document.getElementById("email").innerText =
            data.email;

        document.getElementById("role").innerText =
            data.role;

        document.getElementById("badge").innerText =
            data.badge;

        document.getElementById("projects").innerText =
            data.totalProjects;

        document.getElementById("assigned").innerText =
            data.assignedTasks;

        document.getElementById("completed").innerText =
            data.completedTasks;

        document.getElementById("pending").innerText =
            data.pendingTasks;

        document.getElementById("progress").innerText =
            data.inProgressTasks;

        document.getElementById("overdue").innerText =
            data.overdueTasks;

        document.getElementById("rate").innerText =
            data.completionRate + "%";

        document.getElementById("efficiency").innerText =
            data.efficiencyScore;

        document.getElementById("progressBar").style.width =
            data.completionRate + "%";

    } catch (error) {
        alert(error.message);
    }
}