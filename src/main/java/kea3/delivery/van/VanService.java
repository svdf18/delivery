package kea3.delivery.van;

import kea3.delivery.delivery.Delivery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VanService {

    private final VanRepository vanRepository;

    public VanService(VanRepository vanRepository) {
        this.vanRepository = vanRepository;
    }

    public Optional<Van> getVanById(Long id) {
        return vanRepository.findById(Math.toIntExact(id));
    }

    public boolean addDeliveryToVan(Delivery delivery, Long vanId) {
        Van van = vanRepository.findById(Math.toIntExact(vanId)).orElseThrow();
        return addDeliveryToVan(delivery, van);
    }

    public boolean addDeliveryToVan(Delivery delivery, Van van) {
        if(van.getCombinedWeightOfDeliveriesInKg() + delivery.getTotalWeightInKg() <= van.getCapacityInKg()) {
            van.getDeliveries().add(delivery);
        } else {
            throw new IllegalArgumentException("Van cannot carry delivery");
        }
        vanRepository.save(van);
        return true;
    }

    public List<Van> getAllVans() {
        return vanRepository.findAll();
    }
}
