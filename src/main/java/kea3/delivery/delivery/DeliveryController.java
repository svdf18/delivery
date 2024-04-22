package kea3.delivery.delivery;

import kea3.delivery.productorder.ProductOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping()
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id)
                .map(delivery -> ResponseEntity.ok(deliveryService.convertToDTO(delivery)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createEmptyDelivery() {
        Long deliveryId = deliveryService.createEmptyDelivery();
        return ResponseEntity.ok().body(deliveryId);
    }

    @PutMapping("/{deliveryId}/product-order")
    public ResponseEntity<DeliveryDTO> addProductOrderToDelivery(@PathVariable Long deliveryId,
                                                                 @RequestBody ProductOrderDTO productOrderDTO) {
        Delivery delivery = deliveryService.addProductOrderToDelivery(deliveryId, productOrderDTO);
        return ResponseEntity.ok(deliveryService.convertToDTO(delivery));
    }

    @GetMapping("/{deliveryId}/product-orders")
    public ResponseEntity<List<ProductOrderDTO>> getProductOrdersForDelivery(@PathVariable Long deliveryId) {
        List<ProductOrderDTO> productOrders = deliveryService.getProductOrdersForDelivery(deliveryId);
        return ResponseEntity.ok().body(productOrders);
    }
}