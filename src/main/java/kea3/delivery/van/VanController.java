package kea3.delivery.van;

import kea3.delivery.delivery.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vans")
public class VanController {

    private final VanService vanService;

    public VanController(VanService vanService) {
        this.vanService = vanService;
    }

    @GetMapping()
    List<Van> getAllVans() {
        return vanService.getAllVans();
    }

    @GetMapping("{id}")
    public ResponseEntity<Van> getVanById(@PathVariable Long id) {
        return vanService.getVanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("{id}/deliveries")
    public ResponseEntity<Van> addDeliveryToVan(@PathVariable Long id, @RequestBody Delivery delivery) {
        Van van = vanService.getVanById(id).orElseThrow();
        if (vanService.addDeliveryToVan(delivery, van)) {
            return ResponseEntity.ok(van);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
