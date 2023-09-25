package dev.scaler.productservice.services;

import dev.scaler.productservice.Exceptions.NotFoundException;
import dev.scaler.productservice.dtos.FakeStoreProductDto;
import dev.scaler.productservice.dtos.GenericProductDto;
import dev.scaler.productservice.models.Category;
import dev.scaler.productservice.models.Product;
import dev.scaler.productservice.repositories.CategoryRepository;
import dev.scaler.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    @Autowired CategoryRepository categoryRepository;
    SelfProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException {
        Optional<Product> op =productRepository.findById(id);
        Product p;
        if(op.isEmpty())
            throw new NotFoundException("Product with "+id +" is not present");
        else {
             p = op.get();
        }
        return new GenericProductDto(p.getId(),p.getTitle(),p.getDescription(),p.getImage(),p.getCategory().getName(),p.getPrice());

    }

    @Override
    public List<FakeStoreProductDto> getProducts() {
        List<Product> products=productRepository.findAll();
        List<FakeStoreProductDto> fakeStoreProductDtos = new ArrayList<>();
        for(Product p:products){
            fakeStoreProductDtos.add( new FakeStoreProductDto(p.getId(),p.getTitle(),p.getPrice(),p.getCategory().getName().toString(),p.getDescription(),p.getImage()));
        }
        return fakeStoreProductDtos;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        Category c= categoryRepository.findByName(category);
        return productRepository.findAllByCategory(c);
    }

    @Override
    public Product updateProduct(String id,GenericProductDto product) {
        Product p ;

        Optional<Product> op = productRepository.findById(Long.parseLong(id));
        if(op.isEmpty()){
           p = new Product();
        }
        else {
            p = op.get();
        }
        p.setImage(product.getImage());
        p.setTitle(product.getTitle());
        Category c=categoryRepository.findByName(product.getCategory());
        if(c==null) {
            c= new Category();
            c.setName(product.getCategory());
            p.setCategory(categoryRepository.save(c));
        }
        else
            p.setCategory(c);
        p.setPrice(product.getPrice());
        p.setDescription(product.getDescription());
          return   productRepository.save(p);


    }

    @Override
    public Product deleteProduct(Long id) {
        Product p =productRepository.findById(id).get();
        productRepository.deleteById(id);
        return p;

    }

    @Override
    public Product createProduct(GenericProductDto product) {
        Product p=new Product();
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setTitle(product.getTitle());
        Category c=categoryRepository.findByName(product.getCategory());
        if(c==null) {
            c= new Category();
            c.setName(product.getCategory());
            p.setCategory(categoryRepository.save(c));
        }
        else
            p.setCategory(c);
        p.setImage(product.getImage());
        Product savedProduct=productRepository.save(p);

        return savedProduct;
    }

}
