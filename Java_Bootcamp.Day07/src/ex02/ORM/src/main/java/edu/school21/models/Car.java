package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "car")
public class Car {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "marka", length = 10)
    private String marka;
    @OrmColumn(name = "model", length = 10)
    private String model;
    @OrmColumn(name = "year")
    private Integer year;
}


