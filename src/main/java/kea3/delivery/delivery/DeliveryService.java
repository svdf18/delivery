package kea3.delivery.delivery;

import kea3.delivery.product.Product;
import kea3.delivery.product.ProductRepository;
import kea3.delivery.productorder.ProductOrder;
import kea3.delivery.productorder.ProductOrderDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, ProductRepository productRepository) {
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long createEmptyDelivery() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(LocalDate.now());
        delivery.setWarehouse("Warehouse");
        delivery.setDestination("Destination");
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return savedDelivery.getId();
    }

    public Delivery addProductOrderToDelivery(Long deliveryId, ProductOrderDTO productOrderDTO) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with ID: " + deliveryId));

        Product product = productRepository.findById(productOrderDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productOrderDTO.getId()));

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setQuantity(productOrderDTO.getQuantity());

        delivery.addProductOrder(productOrder);
        return deliveryRepository.save(delivery);
    }

    public List<ProductOrderDTO> getProductOrdersForDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with ID: " + deliveryId));

        return delivery.getProductOrders().stream()
                .map(productOrder -> {
                    ProductOrderDTO dto = new ProductOrderDTO();
                    dto.setId(productOrder.getId());
                    dto.setProductId(productOrder.getProduct().getId());
                    dto.setQuantity(productOrder.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Delivery convertToEntity(DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setId(deliveryDTO.getId());
        delivery.setDeliveryDate(LocalDate.parse(deliveryDTO.getDeliveryDate()));
        delivery.setWarehouse(deliveryDTO.getWarehouse());
        delivery.setDestination(deliveryDTO.getDestination());
        return delivery;
    }

    public DeliveryDTO convertToDTO(Delivery delivery) {
        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(delivery.getId());
        deliveryDTO.setDeliveryDate(delivery.getDeliveryDate().toString());
        deliveryDTO.setWarehouse(delivery.getWarehouse());
        deliveryDTO.setDestination(delivery.getDestination());
        return deliveryDTO;
    }
}
