package ru.smal.jwtdemo.mapper;

public interface TypeMapper <T, V>{
    T map(V v);
}
