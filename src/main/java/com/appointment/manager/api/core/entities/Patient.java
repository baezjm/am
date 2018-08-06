package com.appointment.manager.api.core.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Patient{
	private String cuil;
	private String celphoneNumber;
}
