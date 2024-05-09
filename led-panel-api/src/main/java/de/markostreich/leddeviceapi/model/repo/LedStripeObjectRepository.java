package de.markostreich.leddeviceapi.model.repo;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.leddeviceapi.model.LedDevice;
import de.markostreich.leddeviceapi.model.LedStripeObject;

public interface LedStripeObjectRepository extends CrudRepository<LedStripeObject, String>{

	LedStripeObject findByDevice(LedDevice device);

}
