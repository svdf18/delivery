package kea3.delivery.config;

import kea3.delivery.delivery.Delivery;
import kea3.delivery.delivery.DeliveryRepository;
import kea3.delivery.product.Product;
import kea3.delivery.product.ProductRepository;
import kea3.delivery.productorder.ProductOrder;
import kea3.delivery.productorder.ProductOrderRepository;
import kea3.delivery.van.Van;
import kea3.delivery.van.VanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final DeliveryRepository deliveryRepository;
    private final VanRepository vanRepository;

    public InitData(ProductRepository productRepository, ProductOrderRepository productOrderRepository, DeliveryRepository deliveryRepository, VanRepository vanRepository) {
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.vanRepository = vanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("InitData created");
        createData();
    }

    private void createData() {
        System.out.println("Data created");

        List<Product> products = new ArrayList<>();
        products.add(new Product("Tofu", 99.99, 500));
        products.add(new Product("Mirin", 49.99, 300));
        products.add(new Product("Soy Sauce", 29.99, 400));
        products.add(new Product("Rice Vinegar", 39.99, 350));
        products.add(new Product("Sesame Oil", 69.99, 250));
        products.add(new Product("Miso Paste", 79.99, 200));
        products.add(new Product("Nori Sheets", 19.99, 150));
        products.add(new Product("Sushi Rice", 59.99, 100));
        products.add(new Product("Bonito Flakes", 89.99, 150));
        products.add(new Product("Dashi Powder", 99.99, 200));
        products.add(new Product("MEGA Pow", 99.99, 99000));

        productRepository.saveAll(products);

        List<ProductOrder> productOrders = new ArrayList<>();
        productOrders.add(new ProductOrder(null, products.get(0), 10));
        productOrders.add(new ProductOrder(null, products.get(1), 10));
        productOrders.add(new ProductOrder(null, products.get(2), 10));
        productOrders.add(new ProductOrder(null, products.get(3), 10));
        productOrders.add(new ProductOrder(null, products.get(4), 10));
        productOrders.add(new ProductOrder(null, products.get(5), 10));

        productOrderRepository.saveAll(productOrders);

        Delivery delivery1 = new Delivery(LocalDate.now(), "Warehouse 1", "Destination 1");
        Delivery delivery2 = new Delivery(LocalDate.now(), "Warehouse 2", "Destination 2");

        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);

        Van van1 = new Van("Ford", "Transit", 1000);
        Van van2 = new Van("Mercedes", "Sprinter", 1500);

        vanRepository.save(van1);
        vanRepository.save(van2);
    }
}