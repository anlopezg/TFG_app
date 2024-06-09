package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.ProductImages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static es.udc.paproject.backend.rest.dtos.SectionDto.toSectionDto;
import static es.udc.paproject.backend.rest.dtos.ToolDto.toToolDto;
import static es.udc.paproject.backend.rest.dtos.YarnDto.toYarnDto;

public class ProductConversor {

    private ProductConversor(){}


    /* Product and Product Summary Conversor */
    public final static List<ProductDto> toProductDtos(List<Product> products){
        return products.stream().map(o-> toProductDto(o)).collect(Collectors.toList());
    }

    public final static ProductDto toProductDto(Product product){

        int amount = 1;

        if(product instanceof Physical){
            Physical physical = (Physical) product;
            amount = physical.getAmount();
        }

        return new ProductDto(product.getId(), product.getUser().getId(),  product.getCraft().getId(),
                product.getSubcategory().getId(),
                product.getTitle(), product.getDescription(), product.getPrice(), product.getActive(),
                product.getUser().getUsername(), product.getProductType(), amount ,productImagesToStringList(product),
                product.getAvgRating());
    }


    public final static List<ProductSummaryDto> toProductSummaryDtos(List<Product> products){
        return products.stream().map(p -> toProductSummaryDto(p)).collect(Collectors.toList());
    }

    public final static ProductSummaryDto toProductSummaryDto(Product product){
        return new ProductSummaryDto(product.getId(), product.getUser().getId(), product.getCraft().getId(),
                product.getSubcategory().getId(), product.getTitle(), product.getPrice(), product.getClass().getSimpleName(),
                product.getUser().getUsername(), product.getImages().iterator().next().getImageUrl());
    }


    /* Product Image Conversor */
    public final static ProductImagesDto toProductImagesDto(ProductImages image){
        return new ProductImagesDto(image.getImageUrl());
    }

    public final static List<String> productImagesToStringList(Product product) {

        List<String> imageUrls = product.getImages().stream()
                .map(image -> toProductImagesDto(image).getImageUrl())
                .collect(Collectors.toList());

        return imageUrls;
    }


    public final static List<PhysicalDto> toPhysicalDtos(List<Physical> physicals){
        return physicals.stream().map(o-> toPhysicalDto(o)).collect(Collectors.toList());

    }

    public static ProductDto toProductDtoType(Product product) {
        if (product instanceof Physical) {
            return toPhysicalDto((Physical) product);
        } else if (product instanceof Pattern) {
            PatternDto patternDto = toPatternDto((Pattern) product);
            patternDto.setAmount(1);
            return patternDto;
        } else {
            throw new IllegalArgumentException("Unknown product type");
        }
    }

    private static void copyCommonAttributes(Product product, ProductDto dto) {
        dto.setId(product.getId());
        dto.setUserId(product.getUser().getId());
        dto.setCraftId(product.getCraft().getId());
        dto.setSubcategoryId(product.getSubcategory().getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setActive(product.getActive());
        dto.setUsername(product.getUser().getUsername());
        dto.setImagesUrl(productImagesToStringList(product));
        dto.setAvgRating(product.getAvgRating());
        dto.setProductType(product.getClass().getSimpleName());
    }

    public static PhysicalDto toPhysicalDto(Physical physical) {
        PhysicalDto dto = new PhysicalDto();
        copyCommonAttributes(physical, dto);

        dto.setAmount(physical.getAmount());
        dto.setColor(physical.getColor());
        dto.setDetails(physical.getDetails());
        dto.setSize(physical.getSize());

        return dto;
    }

    public static PatternDto toPatternDto(Pattern pattern) {
        PatternDto dto = new PatternDto();
        copyCommonAttributes(pattern, dto);

        dto.setDifficultyLevel(pattern.getDifficultyLevel());
        dto.setTime(pattern.getTime());
        dto.setLanguage(pattern.getLanguage());

        return dto;
    }


}
