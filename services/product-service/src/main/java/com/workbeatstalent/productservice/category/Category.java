package com.workbeatstalent.productservice.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.workbeatstalent.productservice.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq")
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE) // REMOVE - if a category is removed, this will also removed related products
    @JsonBackReference
    private List<Product> products = new ArrayList<>();
}
