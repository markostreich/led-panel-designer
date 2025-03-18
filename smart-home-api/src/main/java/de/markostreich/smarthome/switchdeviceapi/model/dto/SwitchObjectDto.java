package de.markostreich.smarthome.switchdeviceapi.model.dto;

public record SwitchObjectDto(
		String name,
		boolean state,
		Integer duration,
		String deviceName) {}
