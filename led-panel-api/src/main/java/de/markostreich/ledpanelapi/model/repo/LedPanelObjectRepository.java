package de.markostreich.ledpanelapi.model.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.markostreich.ledpanelapi.model.LedPanelDevice;
import de.markostreich.ledpanelapi.model.LedPanelObject;

public interface LedPanelObjectRepository extends CrudRepository<LedPanelObject, String> {

	List<LedPanelObject> findByDevice(LedPanelDevice device);

	LedPanelObject findByName(String name);
}
