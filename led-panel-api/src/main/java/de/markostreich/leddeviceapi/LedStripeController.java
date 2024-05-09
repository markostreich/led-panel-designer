package de.markostreich.leddeviceapi;

import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.markostreich.leddeviceapi.model.LedStripeMode;
import de.markostreich.leddeviceapi.model.LedStripeObject;
import de.markostreich.leddeviceapi.model.dto.LedStripeObjectDto;
import de.markostreich.leddeviceapi.model.repo.LedDeviceRepository;
import de.markostreich.leddeviceapi.model.repo.LedStripeObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@Profile("!testmode")
@RequestMapping("/ledstripe")
@RequiredArgsConstructor
@Slf4j
public class LedStripeController {

	private final LedStripeObjectRepository ledStripeObjectRepository;
	private final LedDeviceRepository ledDeviceRepository;

	@GetMapping(path = "/update/{device}", produces = "application/json")
	public ResponseEntity<LedStripeObjectDto> update(@PathVariable(name = "device") final String deviceName) {
		log.info("Searching data for '{}'...", deviceName);
		val ledDevice = ledDeviceRepository.findByName(deviceName);
		if (Objects.isNull(ledDevice)) {
			log.warn("Could not find device '{}'", deviceName);
			return ResponseEntity.notFound().build();
		}
		val ledStripeObject = ledStripeObjectRepository.findByDevice(ledDevice);
		if (Objects.isNull(ledStripeObject)) {
			log.warn("Could not find led stripe data for device '{}'.", deviceName);
			return ResponseEntity.noContent().build();
		}
		log.info("Found data for device '{}':", deviceName);
		log.info(ledStripeObject.toString());
		return ResponseEntity.ok(new LedStripeObjectDto(ledStripeObject.getMode().toString(), ledStripeObject.getRed(),
				ledStripeObject.getGreen(), ledStripeObject.getBlue(), ledStripeObject.getBrightness(),
				ledStripeObject.getDevice().getName()));
	}

	@PostMapping(path = "/stripeobject", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> postLedStripeObject(@RequestBody final LedStripeObjectDto objectDto) {
		val device = ledDeviceRepository.findByName(objectDto.deviceName());
		if (Objects.isNull(device)) {
			log.warn("No device found with name '{}'.", objectDto.deviceName());
			return ResponseEntity.noContent().build();
		}
		val existingLedStripeObject = device.getLedStripeObject();
		if (Objects.nonNull(existingLedStripeObject)) {
			existingLedStripeObject.setMode(LedStripeMode.valueOf(objectDto.mode()));
			existingLedStripeObject.setRed(objectDto.red());
			existingLedStripeObject.setGreen(objectDto.green());
			existingLedStripeObject.setBlue(objectDto.blue());
			existingLedStripeObject.setBrightness(objectDto.brightness());
			ledStripeObjectRepository.save(existingLedStripeObject);
			return ResponseEntity.accepted().build();
		}
		var createdObject = LedStripeObject.builder().mode(LedStripeMode.valueOf(objectDto.mode())).red(objectDto.red())
				.green(objectDto.green()).blue(objectDto.blue()).brightness(objectDto.brightness()).device(device)
				.build();
		createdObject = ledStripeObjectRepository.save(createdObject);
		val location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/update").path("/{device}")
				.buildAndExpand(createdObject.getDevice().getName()).toUri();
		return ResponseEntity.created(location).build();
	}
}
