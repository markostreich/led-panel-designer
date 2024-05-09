package de.markostreich.leddeviceapi.model.dto;

public record LedPanelObjectDto(
		String name,
		int pox_x,
		int pos_y,
		int rotationPoint_x,
		int rotationPoint_y,
		String imageData,
		String deviceName) {};