package kea3.delivery.delivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDTO {

        private Long id;
        private String deliveryDate;
        private String warehouse;
        private String destination;
}
