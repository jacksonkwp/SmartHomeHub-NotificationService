package com.smartHomeHub.notification.DTO;

import com.smartHomeHub.notification.model.Stream;

import lombok.Data;

@Data
public class StreamDTO {
	
	private long id;
	private String name;
	
	public StreamDTO(Stream stream) {
		this.id = stream.getId();
		this.name = stream.getName();
	}
}
