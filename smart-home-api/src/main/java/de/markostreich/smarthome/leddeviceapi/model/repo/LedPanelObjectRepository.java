package de.markostreich.smarthome.leddeviceapi.model.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.smarthome.leddeviceapi.model.LedDevice;
import de.markostreich.smarthome.leddeviceapi.model.LedPanelObject;

public interface LedPanelObjectRepository extends CrudRepository<LedPanelObject, String> {

	List<LedPanelObject> findByDevice(LedDevice device);

	LedPanelObject findByName(String name);
}
