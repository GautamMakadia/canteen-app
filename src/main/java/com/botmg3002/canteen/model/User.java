package com.botmg3002.canteen.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user",cascade = CascadeType.ALL)
    private Admin admin;
    
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    @Override
    public String toString() {
        return "user";
    }
    
}
