package kea3.delivery.productorder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderDTO {

    private Long id;
    private Long productId;
    private int quantity;
}
