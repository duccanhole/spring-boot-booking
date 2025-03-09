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
			if(!response.ok) {
				const json = await response.json()
				throw new Error(json.message);
			}
			return await response.json();
		} catch (error) {
			console.log(error)
			throw error;
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

const provinces = [
	'An Giang', 'Bà Rịa – Vũng Tàu', 'Bạc Liêu',
	'Bắc Giang', 'Bắc Kạn', 'Bắc Ninh',
	'Bến Tre', 'Bình Dương', 'Bình Định',
	'Bình Phước', 'Bình Thuận', 'Cà Mau',
	'Cao Bằng', 'Cần Thơ', 'Đà Nẵng',
	'Đắk Lắk', 'Đắk Nông', 'Điện Biên',
	'Đồng Tháp', 'Gia Lai', 'Hà Giang',
	'Hà Nam', 'Hà Nội', 'Hà Tĩnh',
	'Hải Dương', 'Hải Dương', 'Hậu Giang',
	'Hòa Bình', 'Thành phố Hồ Chí Minh', 'Hưng Yên',
	'Khánh Hòa', 'Kiên Giang', 'Kon Tum',
	'Lai Châu', 'Lạng Sơn', 'Lào Cai',
	'Lâm Đồng', 'Long An', 'Nam Định',
	'Nghệ An', 'Ninh Bình', 'Ninh Thuận',
	'Phú Thọ', 'Phú Yên', 'Quảng Bình',
	'Quảng Nam', 'Quảng Ngãi', 'Quảng Ninh',
	'Quảng Trị', 'Sóc Trăng', 'Sơn La',
	'Tây Ninh', 'Thái Bình', 'Thái Nguyên',
	'Thanh Hóa', 'Thừa Thiên Huế', 'Tiền Giang',
	'Trà Vinh', 'Tuyên Quang', 'Vĩnh Long',
	'Vĩnh Phúc', 'Yên Bái'
]