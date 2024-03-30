package de.markostreich.ledpanelapi.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedPanelObject {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2")
	@Column(unique = true, nullable = false, length = 36)
	private String id;

	private String name;

	private int x;

	private int y;

	private int rotationPointX;

	private int rotationPointY;

	private byte[] data;
	
	@ManyToOne
	private LedPanelDevice device;
}
