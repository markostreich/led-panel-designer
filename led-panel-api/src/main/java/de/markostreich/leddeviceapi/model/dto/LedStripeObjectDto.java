package de.markostreich.leddeviceapi.model.dto;

public record LedStripeObjectDto(String mode, byte red, byte green, byte blue, byte brightness, String deviceName) {
}
