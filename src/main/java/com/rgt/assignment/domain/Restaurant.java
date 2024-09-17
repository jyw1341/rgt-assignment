package com.rgt.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    private Long id;

    private String name;

    private Integer tableQuantity;

    public Restaurant(Long id, String name, Integer tableQuantity) {
        this.id = id;
        this.name = name;
        this.tableQuantity = tableQuantity;
    }
}
