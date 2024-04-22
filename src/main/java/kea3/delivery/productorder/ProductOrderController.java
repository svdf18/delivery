package kea3.delivery.productorder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/product-orders")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @GetMapping()
    public List<ProductOrderDTO> getAllProductOrders() {
        return productOrderService.getAllProductOrders();
    }

    @PostMapping("/create")
    public ResponseEntity<ProductOrderDTO> createProductOrder(@RequestBody ProductOrderDTO productOrderRequest) {
        try {
            ProductOrderDTO productOrderDTO = productOrderService.createProductOrder(productOrderRequest);
            return ResponseEntity.ok(productOrderDTO);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product order request", ex);
        }
    }
}