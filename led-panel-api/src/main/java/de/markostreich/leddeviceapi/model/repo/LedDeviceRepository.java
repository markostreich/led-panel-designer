package de.markostreich.leddeviceapi.model.repo;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.leddeviceapi.model.LedDevice;

public interface LedDeviceRepository extends CrudRepository<LedDevice, String>{

	LedDevice findByName(String name);
}
