package de.markostreich.smarthome.leddeviceapi.model.repo;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.smarthome.leddeviceapi.model.LedDevice;

public interface LedDeviceRepository extends CrudRepository<LedDevice, String>{

	LedDevice findByName(String name);
}
