package de.markostreich.smarthome.leddeviceapi.model.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.smarthome.deviceapi.model.Device;
import de.markostreich.smarthome.leddeviceapi.model.LedPanelObject;

public interface LedPanelObjectRepository extends CrudRepository<LedPanelObject, String> {

	List<LedPanelObject> findByDevice(Device device);

	LedPanelObject findByName(String name);
}
