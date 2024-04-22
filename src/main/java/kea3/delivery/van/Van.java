package kea3.delivery.van;

import jakarta.persistence.*;
import kea3.delivery.delivery.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Van {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int capacityInKg;

    @OneToMany
    private List<Delivery> deliveries;

    public Van(String brand, String model, int capacityInKg) {
        this.brand = brand;
        this.model = model;
        this.capacityInKg = capacityInKg;
    }

    public int getCombinedWeightOfDeliveriesInKg() {
        int combinedWeightOfDeliveries = 0;

        for (Delivery delivery: getDeliveries()) {
            combinedWeightOfDeliveries += (int) delivery.getTotalWeightInKg();
        }
        return combinedWeightOfDeliveries;
    }

    public boolean canCarryDelivery(Delivery delivery) {
        return getCombinedWeightOfDeliveriesInKg() + delivery.getTotalWeightInKg() <= capacityInKg;
    }
}
