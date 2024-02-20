package Reddis.demo.Controller;

import Reddis.demo.entity.Product;
import Reddis.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Product")
@EnableCaching
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping
    @Cacheable(value = "Product")
    public List<Object> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    @Cacheable(key="#id",value = "Product",unless = "#result.qty > 1")
    public Product findProduct(@PathVariable int id) {
        return productRepository.findProductById(id);
    }

    @DeleteMapping("{id}")
    public String remove(@PathVariable int id){
        return productRepository.deleteProduct(id);
    }
}
