package dev.scaler.productservice;

import dev.scaler.productservice.models.Category;
import dev.scaler.productservice.models.Product;
import dev.scaler.productservice.repositories.CategoryRepository;
import dev.scaler.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductserviceApplication implements CommandLineRunner {

    @Autowired CategoryRepository categoryRepository;
    @Autowired ProductRepository productRepository;
    public static void main(String[] args) {
        SpringApplication.run(ProductserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category c1 = new Category();
        c1.setName("Mobiles");
        Category savedCategory=categoryRepository.save(c1);
        Category c2 = new Category();
        c2.setName("Washing Machine");
        Category savedCategory2 = categoryRepository.save(c2);
        Product p =new Product();
        p.setCategory(savedCategory);
        p.setTitle("IPhone14");
        p.setPrice(50000);
        p.setDescription("Apple Phones");

        Product p1 =new Product();
        p1.setCategory(savedCategory);
        p1.setTitle("IPhone15");
        p1.setPrice(80000);
        p1.setDescription("Apple Phones");

        Product p2 =new Product();
        p2.setCategory(savedCategory2);
        p2.setTitle("Samsung E12 Semi Automatic");
        p2.setPrice(15000);
        p2.setDescription("Samsung Machines");
        productRepository.save(p2);
        productRepository.save(p);
        productRepository.save(p1);
    }
}
