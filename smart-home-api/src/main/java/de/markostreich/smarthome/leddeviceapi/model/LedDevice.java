package de.markostreich.smarthome.leddeviceapi.model;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedDevice {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private UUID id;

	@Column(unique = true)
	private String name;

	private Timestamp lastLogin;

	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
	private Set<LedPanelObject> ledPanelObjects;

	@OneToOne(mappedBy = "device")
	@PrimaryKeyJoinColumn
	private LedStripeObject ledStripeObject;
}