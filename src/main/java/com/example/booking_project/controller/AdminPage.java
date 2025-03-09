package com.example.booking_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
class AdminPage {
//	@GetMapping("/template")
//	public String getTemplate() {
//		return "admin-template";
//	}
//	@GetMapping("/user")
//	public String getUserPage() {
//		return "admin-user";
//	}
//	@GetMapping("/user/create")
//	public String getCreateUserPage() {
//		return "admin-user-create";
//	}
//	@GetMapping("/user/update/{id}")
//	public String getUpdateUserPage() {
//		return "admin-user-update";
//	}
//	
//	@GetMapping("/bus")
//	public String getBusPage() {
//		return "admin-bus";
//	}
	
	@GetMapping("/{entity}")
	public String getEntityPage(@PathVariable("entity") String entity) {
		return String.format("admin-%s", entity);
	}
	
	@GetMapping("/{entity}/create")
	public String getCreateEntityPage(@PathVariable("entity") String entity) {
		return String.format("admin-%s-create", entity);
	}
	
	@GetMapping("/{entity}/update/{id}")
	public String getUpdateEntityPage(@PathVariable("entity") String entity) {
		return String.format("admin-%s-update", entity);
	}
}