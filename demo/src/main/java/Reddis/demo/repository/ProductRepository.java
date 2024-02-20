package Reddis.demo.repository;

import Reddis.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private static final String HASH_KEY = "Product";

    //    @Autowired
//    public RedisTemplate template;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> template;

    public Product save(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }


    public List<Object> findAll() {
        System.out.println("Retrieving All Products from DataBase");
        return template.opsForHash().values(HASH_KEY);
    }


    public Product findProductById(int id) {
        System.out.println("Retrieving from DataBase by Product Id");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Product Removed!!!!!";

    }

}

