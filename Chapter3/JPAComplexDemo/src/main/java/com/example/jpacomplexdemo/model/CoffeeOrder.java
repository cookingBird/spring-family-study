package com.example.jpacomplexdemo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "T_ORDER")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder extends BaseEntity implements Serializable {


    private String consumer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("createTime")
    private List<Coffee> items;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private OrderState state;
}
