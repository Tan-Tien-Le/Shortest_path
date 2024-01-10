package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Setter
@Entity
public class Vertical {
    @Id
    Integer label;
    private double x;
    private double y;

    public Vertical(Integer label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public Vertical(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vertical{" +
                "label=" + label +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

