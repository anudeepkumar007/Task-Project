package com.personal.project.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 

public class JWTAuthResponse {
	private String token;
	private String tokenType="Bearer";
	
	public JWTAuthResponse(String token) {
		this.token=token;
	}
}
