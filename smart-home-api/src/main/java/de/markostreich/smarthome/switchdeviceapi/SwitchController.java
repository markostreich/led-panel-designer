package de.markostreich.smarthome.switchdeviceapi;

import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.markostreich.smarthome.deviceapi.model.repo.DeviceRepository;
import de.markostreich.smarthome.switchdeviceapi.model.dto.SwitchObjectDto;
import de.markostreich.smarthome.switchdeviceapi.model.repo.SwitchObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@RestController
@Profile("!testmode")
@RequestMapping("/switch")
@RequiredArgsConstructor
@Slf4j
public class SwitchController {
	
	private final SwitchObjectRepository switchObjectRepository;
	private final DeviceRepository deviceRepository;
	
	@GetMapping(path = "/update/{device}", produces = "application/json")
	public ResponseEntity<SwitchObjectDto> update(@PathVariable(name = "device") final String deviceName) {
		log.info("Searching data for device '{}' ...", deviceName);
		val device = deviceRepository.findByName(deviceName);
		if (Objects.isNull(device)) {
			log.warn("Could not find device '{}'.", deviceName);
			return ResponseEntity.notFound().build();
		}
		val switchObjectList = switchObjectRepository.findByDevice(device);
		if (switchObjectList.isEmpty()) {
			log.warn("Could not find switch data for device '{}'.", deviceName);
			return ResponseEntity.noContent().build();
		}
		if (switchObjectList.size() != 1) {
			log.warn("Found more than one switch data for device '{}'.", deviceName);
			return ResponseEntity.badRequest().build();
		}
		val switchObject = switchObjectList.get(0);
		log.info("Found data for device '{}':", deviceName);
		return ResponseEntity.ok(new SwitchObjectDto(switchObject.getName(), switchObject.isState(), switchObject.getDevice().getName()));
	}

}
