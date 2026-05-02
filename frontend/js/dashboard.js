checkAuth();
loadDashboard();

async function loadDashboard(){
    try{
        const data = await apiRequest("/dashboard");

        document.getElementById("totalProjects").innerText =
            data.totalProjects;

        document.getElementById("totalTasks").innerText =
            data.totalTasks;

        document.getElementById("pendingTasks").innerText =
            data.pendingTasks;

        document.getElementById("inProgressTasks").innerText =
            data.inProgressTasks;

        document.getElementById("completedTasks").innerText =
            data.completedTasks;

        document.getElementById("overdueTasks").innerText =
            data.overdueTasks;

    }catch(error){
        console.error(error);
    }
}