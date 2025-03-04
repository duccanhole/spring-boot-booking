package com.example.booking_project.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
	@JsonProperty("name")
	public String name;

    @JsonProperty("email")
    public String email;

    @JsonProperty("phone")
    public String phone;

    @JsonProperty("password")
    public String password;

    @JsonProperty("role")
    public String role;
}
