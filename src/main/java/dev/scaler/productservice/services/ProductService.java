package dev.scaler.productservice.services;

import dev.scaler.productservice.Exceptions.NotFoundException;
import dev.scaler.productservice.dtos.FakeStoreProductDto;
import dev.scaler.productservice.dtos.GenericProductDto;
import dev.scaler.productservice.models.Category;
import dev.scaler.productservice.models.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(GenericProductDto product);

    GenericProductDto getProductById(Long id) throws NotFoundException;
    List<FakeStoreProductDto> getProducts();

    List<Category> getCategories();

    List<Product> getProductsByCategory(String category);

    Product updateProduct(String id,GenericProductDto product);

    Product deleteProduct(Long product_id);
}
