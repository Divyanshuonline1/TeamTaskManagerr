async function apiRequest(
    endpoint,
    method = "GET",
    data = null
) {
    const token = getToken();

    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] =
            "Bearer " + token;
    }

    const options = {
        method,
        headers
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(
            CONFIG.BASE_URL + endpoint,
            options
        );

        const contentType =
            response.headers.get("content-type");

        let result;

        if (
            contentType &&
            contentType.includes("application/json")
        ) {
            result = await response.json();
        } else {
            result = await response.text();
        }

        if (!response.ok) {
            throw new Error(
                result.message || result || "Request failed"
            );
        }

        return result;

    } catch (error) {
        console.error(error);
        throw error;
    }
}