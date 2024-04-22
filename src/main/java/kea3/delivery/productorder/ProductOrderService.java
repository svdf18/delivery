package kea3.delivery.productorder;

import kea3.delivery.product.Product;
import kea3.delivery.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;

    public ProductOrderDTO createProductOrder(ProductOrderDTO productOrderRequest) {
        // Fetch the product by ID
        Product product = productRepository.findById(productOrderRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productOrderRequest.getProductId()));

        // Create ProductOrder instance
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setQuantity(productOrderRequest.getQuantity());

        // Save ProductOrder and return DTO
        ProductOrder savedProductOrder = productOrderRepository.save(productOrder);
        return convertToDTO(savedProductOrder);
    }

    private ProductOrderDTO convertToDTO(ProductOrder productOrder) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setId(productOrder.getId());
        productOrderDTO.setProductId(productOrder.getProduct().getId());
        productOrderDTO.setQuantity(productOrder.getQuantity());
        return productOrderDTO;
    }

    public List<ProductOrderDTO> getAllProductOrders() {
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        return productOrders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
