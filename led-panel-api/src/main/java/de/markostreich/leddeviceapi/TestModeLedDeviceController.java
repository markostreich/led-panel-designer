package de.markostreich.leddeviceapi;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.markostreich.leddeviceapi.model.LedDevice;
import de.markostreich.leddeviceapi.model.dto.LedPanelObjectDto;

@RestController
@Profile("testmode")
@RequestMapping("")
public class TestModeLedDeviceController {

	private static String EXAMPLE_IMAGE = "0000A7A700010100A7A70202A700A70303A700A70404A700A70505A700A70606A700A70707A700A70806A800A70909A700A70A0AA700A70B0BA700A7";

	@PostMapping("/device/connect")
	public LedDevice connectDevice(@RequestBody LedDevice device) {
		return device;
	}

	@GetMapping("/ledpanel/update/{device}")
	public LedPanelObjectDto update(@PathVariable final String deviceName) {
		return new LedPanelObjectDto("testJsonName", 7, 7, 7, 7, EXAMPLE_IMAGE, deviceName);
	}
}
