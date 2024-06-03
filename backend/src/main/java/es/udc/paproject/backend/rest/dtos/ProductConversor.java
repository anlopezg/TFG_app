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




    /* Physical Conversor */
    public final static PhysicalDto toPhysicalDto(Physical physical){

        return new PhysicalDto(physical.getId(),
                physical.getUser().getId(),
                physical.getCraft().getId(),
                physical.getSubcategory().getId(),
                physical.getTitle(),
                physical.getDescription(),
                physical.getPrice(),
                physical.getActive(),
                physical.getUser().getUsername(),
                physical.getProductType(),
                physical.getAmount(),
                productImagesToStringList(physical),
                physical.getAvgRating(),
                physical.getSize(),
                physical.getColor(),
                physical.getDetails());
    }

    public final static List<PhysicalDto> toPhysicalDtos(List<Physical> physicals){
        return physicals.stream().map(o-> toPhysicalDto(o)).collect(Collectors.toList());
    }
}
