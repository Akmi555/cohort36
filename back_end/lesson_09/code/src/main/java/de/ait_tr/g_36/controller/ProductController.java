package de.ait_tr.g_36.controller;

import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    // DI
    public ProductController(ProductService service) {
        this.service = service;
    }

    // CRUD - Create(POST), Read (GET), Update (PUT), Delete (DELETE)

    @PostMapping
    public Product save(@RequestBody Product product){
        return service.save(product);
    }


    @GetMapping()
    public List<Product> get(@RequestParam(required = false) Long id){
        // TODO обращаемся к сервису, просим все продукты
        if(id == null){
            return service.getAllProducts();
        } else {
            Product product = service.getById(id);
            return product == null ? null : List.of(product);
        }
    }

    @PutMapping
    public Product update(@RequestBody Product product){
        return service.update(product);
    }

    @DeleteMapping
    public void delete(@RequestParam(required = false) Long id, @RequestParam(required = false) String title){
        if(id != null) {
            service.deleteById(id);
        } else if (title != null){
            service.deleteByTitle(title);
        }
    }

    @PutMapping("/restore")
    public void restore(@RequestParam Long id){
        service.restoreById(id);
    }

    @GetMapping("/quantity")
    public long getProductQuantity(){
        return service.getAllActiveProductsQuantity();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice(){
        return service.getAllActiveProductsTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice(){
        return service.getAllActiveProductsAveragePrice();
    }

}
