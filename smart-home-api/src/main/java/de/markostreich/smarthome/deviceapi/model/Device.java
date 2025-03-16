package de.markostreich.smarthome.deviceapi.model;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import de.markostreich.smarthome.leddeviceapi.model.LedPanelObject;
import de.markostreich.smarthome.leddeviceapi.model.LedStripeObject;
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
public class Device {

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