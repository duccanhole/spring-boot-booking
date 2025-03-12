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
				if (response.status === 403) {
					alert("Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại")
					window.location.href = '/sign-in'
				}
				else {
					const json = await response.json()
					throw new Error(json.message);
				}
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

async function signUp(formData) {
	try {
		const response = await fetch("http://localhost:8080/auth/sign-up", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(formData),
		});

		const data = await response.json();

		if (!response.ok) {
			throw new Error(data.message || "Đăng ký thất bại");
		}

		console.log("Đăng ký thành công:", data);
		// alert("Đăng ký thành công, vui lòng đăng nhập lại để tiếp tục.")
		// window.location.href = '/sign-in'
	} catch (error) {
		throw error
	}
}

async function signIn(formData) {
	try {
		const response = await fetch("http://localhost:8080/auth/sign-in", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(formData),
		});

		const data = await response.json();

		if (!response.ok) {
			throw new Error(data.message || "Đăng ký thất bại");
		}

		console.log("Đăng ký thành công:", data);
		document.cookie = `token=${data.token}`
		document.cookie = `userInfo=${JSON.stringify(data.user)}`
		const role = data.user.role.toLowerCase()
		if (role === 'admin') window.location.href = '/admin/user'
		if (role === 'user') window.location.href = '/user/home'
	} catch (error) {
		// errorMessage.value = error.message;
		console.error(error)
		throw error
	}
}

function signOut() {
	document.cookie.split(";").forEach((cookie) => {
		const name = cookie.split("=")[0].trim();
		document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
	});
	window.location.href = '/sign-in'
}

function getUserInfo() {
	const cookies = document.cookie.split("; ");
	for (let cookie of cookies) {
		const [key, value] = cookie.split("=");
		if (key === "userInfo") {
			return JSON.parse(value);
		}
	}
	return null;
}

const generatePDF = (ticket) => {
	const removeDiacritics = (str) => {
	  return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
	};
	const formatDate = (date) => {
		return new Date(date).toLocaleDateString("vi-VN", {
			timeZone: "Asia/Ho_Chi_Minh",
			hour: "2-digit",
			minute: "2-digit",
			hour12: false, // Use 24-hour format
		})
	}
	const formatCurrency = (amount) => {
		return new Intl.NumberFormat("de-DE").format(amount);
	}
	const { jsPDF } = window.jspdf; // Use from global scope
	const doc = new jsPDF();

	doc.setFont("helvetica");
	doc.text("Thông tin vé xe", 10, 10);
	doc.text(`Ma ve: ${ticket.id}`, 10, 20);
	doc.text(`Diem di: ${removeDiacritics(ticket.seat.schedule.route.departure)}`, 10, 30);
	doc.text(`Diem den: ${removeDiacritics(ticket.seat.schedule.route.arrival)}`, 10, 40);
	doc.text(`Loai xe: ${removeDiacritics(ticket.seat.schedule.bus.type)}`, 10, 50);
	doc.text(`Bien so xe: ${removeDiacritics(ticket.seat.schedule.bus.licensePlate)}`, 10, 60);
	doc.text(`So ghe: ${ticket.seat.seatNumber}`, 10, 70);
	doc.text(`Khoi hanh luc: ${formatDate(ticket.seat.schedule.departureTime)}`, 10, 80);
	doc.text(`Gia ve: ${removeDiacritics(formatCurrency(ticket.seat.schedule.price))} vnd`, 10, 90);
	doc.text(`Trang thai: ${removeDiacritics(ticket.status)}`, 10, 100);

	doc.save(`ticket_${ticket.id}.pdf`);
};