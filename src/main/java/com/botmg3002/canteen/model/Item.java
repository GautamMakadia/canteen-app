package com.botmg3002.canteen.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Check;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Check(constraints = "price > 0")
public class Item implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private Integer price;
    
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubItemType> subItemTypes = new HashSet<>();

    @Column(nullable = false)
    private Boolean isAvailable = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canteenId")
    private Canteen canteen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId") 
    private Category category;
}
