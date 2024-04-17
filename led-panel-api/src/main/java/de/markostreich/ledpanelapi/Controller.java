package de.markostreich.ledpanelapi;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HexFormat;
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

import de.markostreich.ledpanelapi.model.LedPanelDevice;
import de.markostreich.ledpanelapi.model.LedPanelObject;
import de.markostreich.ledpanelapi.model.dto.LedPanelDeviceDto;
import de.markostreich.ledpanelapi.model.dto.LedPanelObjectDto;
import de.markostreich.ledpanelapi.model.repo.LedPanelDeviceRepository;
import de.markostreich.ledpanelapi.model.repo.LedPanelObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@Profile("!testmode")
@RequestMapping("/ledpanel/api")
@RequiredArgsConstructor
@Slf4j
public class Controller {

	private final LedPanelObjectRepository ledPanelObjectRepository;
	private final LedPanelDeviceRepository ledPanelDeviceRepository;
	private final HexFormat hexFormat = HexFormat.of();

	@PostMapping(path = "/connect", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> connectDevice(@RequestBody final LedPanelDeviceDto device) {
		/* Device exists */
		val existingDevice = ledPanelDeviceRepository.findByName(device.name());
		if (Objects.nonNull(existingDevice))
			return ResponseEntity.accepted().build();

		/* add new device */
		var createdDevice = LedPanelDevice.builder().name(device.name()).lastLogin(Timestamp.from(Instant.now()))
				.build();
		createdDevice = ledPanelDeviceRepository.save(createdDevice);
		val location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/update").path("/{device}")
				.buildAndExpand(createdDevice.getName()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping(path = "/update/{device}", produces = "application/json")
	public ResponseEntity<LedPanelObjectDto> update(@PathVariable(name = "device") final String deviceName) {
		log.info(deviceName);
		val ledPanelDevice = ledPanelDeviceRepository.findByName(deviceName);
		if (Objects.isNull(ledPanelDevice))
			return ResponseEntity.notFound().build();
		val ledPanelObjectList = ledPanelObjectRepository.findByDevice(ledPanelDevice);
		if (ledPanelObjectList.isEmpty())
			return ResponseEntity.noContent().build();
		val ledPanelObject = ledPanelObjectList.get(0);
		return ResponseEntity.ok(new LedPanelObjectDto(ledPanelObject.getName(), ledPanelObject.getX(),
				ledPanelObject.getY(), ledPanelObject.getRotationPointX(), ledPanelObject.getRotationPointY(),
				hexFormat.formatHex(ledPanelObject.getData()), deviceName));
	}

	@PostMapping(path = "/object", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> postLedPanelObject(@RequestBody final LedPanelObjectDto objectDto) {
		val device = ledPanelDeviceRepository.findByName(objectDto.deviceName());
		if (Objects.isNull(device))
			return ResponseEntity.noContent().build();
		val exisistingLedPanelObject = ledPanelObjectRepository.findByName(objectDto.name());
		if (Objects.nonNull(exisistingLedPanelObject)
				&& !exisistingLedPanelObject.getDevice().getName().equals(objectDto.deviceName()))
			return ResponseEntity.notFound().build();
		if (Objects.nonNull(exisistingLedPanelObject)) {
			exisistingLedPanelObject.setX(objectDto.pox_x());
			exisistingLedPanelObject.setY(objectDto.pos_y());
			exisistingLedPanelObject.setRotationPointX(objectDto.rotationPoint_x());
			exisistingLedPanelObject.setRotationPointY(objectDto.rotationPoint_y());
			exisistingLedPanelObject.setData(hexStringToByteArray(objectDto.imageData()));
			ledPanelObjectRepository.save(exisistingLedPanelObject);
			return ResponseEntity.accepted().build();
		}
		var createdObject = LedPanelObject.builder().name(objectDto.name()).x(objectDto.pox_x()).y(objectDto.pos_y())
				.rotationPointX(objectDto.rotationPoint_x()).rotationPointY(objectDto.rotationPoint_y())
				.data(hexStringToByteArray(objectDto.imageData())).device(device).build();
		createdObject = ledPanelObjectRepository.save(createdObject);
		val location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/object").path("/{object}")
				.buildAndExpand(createdObject.getName()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping(path = "/object/{object}", produces = "application/json")
	public ResponseEntity<LedPanelObjectDto> getLedPanelObject(@PathVariable(name = "object") final String objectName) {
		val ledPanelObject = ledPanelObjectRepository.findByName(objectName);
		if (Objects.isNull(ledPanelObjectRepository)) {
			return ResponseEntity.notFound().build();
		}
		val responseBody = new LedPanelObjectDto(ledPanelObject.getName(), ledPanelObject.getX(), ledPanelObject.getY(),
				ledPanelObject.getRotationPointX(), ledPanelObject.getRotationPointY(),
				hexFormat.formatHex(ledPanelObject.getData()), ledPanelObject.getDevice().getName());
		return ResponseEntity.ok(responseBody);
	}

	private byte[] hexStringToByteArray(final String hexString) {
		val length = hexString.length();
		val data = new byte[length / 2];
		for (var i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
		}
		return data;
	}

}
