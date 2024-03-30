package de.markostreich.ledpanelapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.markostreich.ledpanelapi.model.LedPanelDevice;
import de.markostreich.ledpanelapi.model.LedPanelObject;

@RestController
@RequestMapping("/ledpanel/api")
public class Controller {

	@PostMapping("/connect")
	public LedPanelDevice connectDevice(@RequestBody LedPanelDevice device) {
		return device;
	}

	@GetMapping("/update/{device}")
	public LedPanelObject update() {
		return null;
	}
}
