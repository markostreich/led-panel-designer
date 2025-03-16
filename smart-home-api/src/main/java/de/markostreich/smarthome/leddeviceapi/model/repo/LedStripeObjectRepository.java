package de.markostreich.smarthome.leddeviceapi.model.repo;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.smarthome.leddeviceapi.model.LedDevice;
import de.markostreich.smarthome.leddeviceapi.model.LedStripeObject;

public interface LedStripeObjectRepository extends CrudRepository<LedStripeObject, String>{

	LedStripeObject findByDevice(LedDevice device);

}
