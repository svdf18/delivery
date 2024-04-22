package kea3.delivery.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            ProductDTO productDTO = productService.convertToDTO(optionalProduct.get());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        Optional<Product> optionalProduct = productService.getProductByName(name);
        return optionalProduct.map(product -> ResponseEntity.ok(productService.convertToDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.convertToEntity(productDTO);
        Product savedProduct = productService.saveProduct(product);
        ProductDTO savedProductDTO = productService.convertToDTO(savedProduct);
        return ResponseEntity.ok(savedProductDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = productService.convertToEntity(productDTO);
            product.setId(id);
            Product savedProduct = productService.saveProduct(product);
            ProductDTO savedProductDTO = productService.convertToDTO(savedProduct);
            return ResponseEntity.ok(savedProductDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.deleteProduct(id);
        return optionalProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
