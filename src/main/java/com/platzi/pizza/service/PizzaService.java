package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

   // private  final JdbcTemplate jdbcTemplate;
    /*@Autowired
    public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;
    @Autowired
   /* public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/
   public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
      this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }
    /*public List<PizzaEntity> getAll(){
       // return this.jdbcTemplate.query("SELECT * FROM pizza",new BeanPropertyRowMapper<>(PizzaEntity.class)) ;
       return this.pizzaRepository.findAll();
    }*/

    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest = PageRequest.of(page,elements);
        return  this.pizzaPagSortRepository.findAll(pageRequest);
    }

    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return  this.pizzaRepository.save(pizza);
    }

    public void  delete(int idPizza){
       this.pizzaRepository.deleteById(idPizza);
    }

    public boolean exists(int idPizza){
       return this.pizzaRepository.existsById(idPizza);

    }

    public List<PizzaEntity> getAvailable(){
        System.out.println(this.pizzaRepository.countByVeganTrue());
       return  this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getByName(String name){
       return  this.pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name);
    }

    public  List<PizzaEntity> getWith(String ingredient){
       return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }
    public  List<PizzaEntity> getWithout(String ingredient){
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public PizzaEntity getByNameFirts(String name){
        //return  this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElse(null);
        return  this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("La pizza no existe"));
    }

    public  List<PizzaEntity> getCheapest(Double price){
        return  this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

}
