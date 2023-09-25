package dev.scaler.productservice.controllers;

import dev.scaler.productservice.Exceptions.NotFoundException;
import dev.scaler.productservice.dtos.FakeStoreProductDto;
import dev.scaler.productservice.dtos.GenericProductDto;
import dev.scaler.productservice.models.Category;
import dev.scaler.productservice.models.Product;
import dev.scaler.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier(value = "selfProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<FakeStoreProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.getProductById(id);
    }


    @DeleteMapping("{id}")
    public Product deleteProductById(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody GenericProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public Product updateProductById(@RequestBody GenericProductDto product,@PathVariable("id") String id) {
        return productService.updateProduct(id,product);
    }

   @GetMapping("/categories")
    public List<Category> getCategories(){
        return productService.getCategories();
   }

    @GetMapping("/categories/{category_name}")
    public List<Product> getProductsByCategory(@PathVariable("category_name") String category){
        return productService.getProductsByCategory(category);
    }
}
