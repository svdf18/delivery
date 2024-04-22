package kea3.delivery.delivery;

import jakarta.persistence.*;
import kea3.delivery.productorder.ProductOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;
    private String warehouse; // TODO: replace with actual warehouse entity
    private String destination;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ProductOrder> productOrders = new ArrayList<>();

    public Delivery(LocalDate deliveryDate, String warehouse, String destination) {
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
        this.destination = destination;
    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrders.add(productOrder);
    }

    public double getTotalWeightInKg() {
        int totalWeight = 0;

        for (ProductOrder productOrder : productOrders) {
            totalWeight += productOrder.getTotalWeightInGrams();
        }

        return (int)Math.ceil((double)totalWeight / 1000);
    }
}
