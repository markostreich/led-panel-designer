package de.markostreich.leddeviceapi;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.markostreich.leddeviceapi.model.LedDevice;
import de.markostreich.leddeviceapi.model.dto.LedDeviceDto;
import de.markostreich.leddeviceapi.model.repo.LedDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@Profile("!testmode")
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
public class LedDeviceController {
	
	private final LedDeviceRepository ledPanelDeviceRepository;

	@PostMapping(path = "/connect", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> connectDevice(@RequestBody final LedDeviceDto device) {
		/* Device exists */
		val existingDevice = ledPanelDeviceRepository.findByName(device.name());
		if (Objects.nonNull(existingDevice)) {
			log.info("Device '{}' connected", existingDevice.getName());
			return ResponseEntity.accepted().build();
		}

		/* add new device */
		log.info("Adding new device '{}'.", device.name());
		var createdDevice = LedDevice.builder().name(device.name()).lastLogin(Timestamp.from(Instant.now())).build();
		createdDevice = ledPanelDeviceRepository.save(createdDevice);
		log.info("Added new device '{}'.", createdDevice.getName());
		val location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/update").path("/{device}")
				.buildAndExpand(createdDevice.getName()).toUri();
		return ResponseEntity.created(location).build();
	}

}
