package de.markostreich.ledpanelapi.model.repo;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.ledpanelapi.model.LedPanelDevice;

public interface LedPanelDeviceRepository extends CrudRepository<LedPanelDevice, String>{

	LedPanelDevice findByName(String name);
}
