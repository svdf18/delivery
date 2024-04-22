package kea3.delivery.productorder;

import jakarta.persistence.*;
import kea3.delivery.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private int quantity;

    public int getTotalWeightInGrams() {
        return product.getWeightInGrams() * quantity;
    }

    public void setProductOrderId(Long id) {
    }
}
