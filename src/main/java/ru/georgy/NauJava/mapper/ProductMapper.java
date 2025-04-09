package ru.georgy.NauJava.mapper;

import org.mapstruct.Mapper;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.service.product.ProductInput;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductInput productInput);
}
