package dev.scaler.productservice.services;

import dev.scaler.productservice.Exceptions.NotFoundException;
import dev.scaler.productservice.dtos.FakeStoreProductDto;
import dev.scaler.productservice.dtos.GenericProductDto;
import dev.scaler.productservice.models.Category;
import dev.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//@Primary
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplateBuilder restTemplateBuilder;
    @Value("${fakeStoreApiUrl}")
    private String baseFakeStoreUrl;

    //private String getProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String createProductRequestUrl = "https://fakestoreapi.com/products";

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product createProduct(GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Product> response = restTemplate.postForEntity(
                createProductRequestUrl, product, Product.class
        );

        return response.getBody();
    }

    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException {
//        FakeStoreProductService fakeStoreProductService = new FakeStoreProductService();
        RestTemplate restTemplate = restTemplateBuilder.build();
        System.out.println("base URL:"+baseFakeStoreUrl);
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(baseFakeStoreUrl+"/{id}", FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null)
            throw new NotFoundException("Product with id:"+id +" does not exist");
        GenericProductDto product = new GenericProductDto();
        product.setId(fakeStoreProductDto.getId());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());
//        response.getStatusCode()

        return product;
//        return null;
    }

    @Override
    public List<FakeStoreProductDto> getProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List> response =
                restTemplate.getForEntity(createProductRequestUrl, List.class);

        List<FakeStoreProductDto> fakeStoreProductDtos = response.getBody();

        return fakeStoreProductDtos;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return null;
    }

    @Override
    public Product updateProduct(String id,GenericProductDto product) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }


}
