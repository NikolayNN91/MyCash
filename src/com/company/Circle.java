package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Circle {


    public Object o;
    public int x;
   int y;
    public int z=5;

    public Circle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Circle() {

    }

    public Circle(Object o) {

        this.o = o;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "o=" + o +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return x == circle.x &&
                y == circle.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    ArrayList list;

}
