package de.markostreich.smarthome.leddeviceapi.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedPanelObject {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private UUID id;

	@Column(unique = true)
	private String name;

	private int x;

	private int y;

	private int rotationPointX;

	private int rotationPointY;

	@Lob
	private byte[] data;

	@ManyToOne
	@JoinColumn(name = "device_id")
	private LedDevice device;
}
