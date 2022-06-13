package pt.ua.clothesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateProductDto {
    private String name;
    private double price;
    private String category;
}
