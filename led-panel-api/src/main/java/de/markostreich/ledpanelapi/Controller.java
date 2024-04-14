package de.markostreich.ledpanelapi;

import java.util.HexFormat;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.markostreich.ledpanelapi.model.LedPanelDevice;
import de.markostreich.ledpanelapi.model.dto.LedPanelObjectDto;
import de.markostreich.ledpanelapi.model.repo.LedPanelDeviceRepository;
import de.markostreich.ledpanelapi.model.repo.LedPanelObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RestController
@Profile("!testmode")
@RequestMapping("/ledpanel/api")
@RequiredArgsConstructor
public class Controller {

	private final LedPanelObjectRepository ledPanelObjectRepository;
	private final LedPanelDeviceRepository ledPanelDeviceRepository;
	private final HexFormat hexFormat = HexFormat.of();

	@PostMapping("/connect")
	public LedPanelDevice connectDevice(@RequestBody final LedPanelDevice device) {
		return device;
	}

	@GetMapping("/update/{device}")
	public LedPanelObjectDto update(@PathVariable final String deviceName) {
		val ledPanelDevice = ledPanelDeviceRepository.findByName(deviceName);
		val ledPanelObjectList = ledPanelObjectRepository.findByDevice(ledPanelDevice);
		if (ledPanelObjectList.isEmpty())
			return null;
		val ledPanelObject = ledPanelObjectList.get(0);
		return new LedPanelObjectDto(ledPanelObject.getName(), ledPanelObject.getX(), ledPanelObject.getY(), ledPanelObject.getRotationPointX(),
				ledPanelObject.getRotationPointY(), hexFormat.formatHex(ledPanelObject.getData()));
	}
}
