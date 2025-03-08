class ApiService {
	constructor(entity) {
		this.entity = entity;
		this.baseUrl = `http://localhost:8080/api/${entity}`;
		this.token = this.getToken();
	}

	getToken() {
		const cookies = document.cookie.split("; ");
		for (let cookie of cookies) {
			const [key, value] = cookie.split("=");
			if (key === "token") {
				return value;
			}
		}
		return null; // Return null if not found
	}

	async request(method, endpoint = "", data = null, query = "") {
		const url = `${this.baseUrl}${endpoint ? `/${endpoint}` : ''}${query ? `?${query}` : ''}`;

		const options = {
			method,
			headers: {
				"Content-Type": "application/json",
				"Authorization": `Bearer ${this.token}`
			}
		};

		if (data) {
			options.body = JSON.stringify(data);
		}

		try {
			const response = await fetch(url, options);
			if (!response.ok) {
				throw new Error(`HTTP error! Status: ${response.status}`);
			}
			return await response.json();
		} catch (error) {
			console.error("API Error:", error);
			return null;
		}
	}

	// CRUD Methods
	create(data) {
		return this.request("POST", "", data);
	}

	update(id, data) {
		return this.request("PUT", id, data);
	}

	get(query = "") {
		return this.request("GET", "", null, query);
	}

	getDetail(id) {
		return this.request("GET", id);
	}

	delete(id) {
		return this.request("DELETE", id);
	}
}

function getParam() {
	const pathParts = window.location.pathname.split("/"); // Split by "/"
	return pathParts[pathParts.length - 1];
}