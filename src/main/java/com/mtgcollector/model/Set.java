package com.mtgcollector.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sets")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}