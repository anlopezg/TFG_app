package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConversor {

    private ProductConversor(){}


    /* Convert Entities: Product, Pattern and Physical to their DTOs */
    public final static List<ProductDto> toProductDtos(List<Product> products){
        return products.stream().map(o-> toProductDto(o)).collect(Collectors.toList());
    }

    public final static ProductDto toProductDto(Product product){
        return new ProductDto(product.getId(), product.getUser().getId(),  product.getCraft().getId(),
                product.getSubcategory().getId(),
                product.getTitle(), product.getDescription(), product.getPrice(), product.getActive());
    }

    public final static PatternDto toPatternDto(Pattern pattern){
        return new PatternDto(pattern.getId(), pattern.getUser().getId(),  pattern.getCraft().getId(),
                pattern.getSubcategory().getId(),
                pattern.getTitle(), pattern.getDescription(), pattern.getPrice(), pattern.getActive(),
                pattern.getIntroduction(), pattern.getNotes(),
                pattern.getGauge(), pattern.getSizing(), pattern.getDifficultyLevel(), pattern.getTime(),
                pattern.getAbbreviations(), pattern.getSpecialAbbreviations(), pattern.getTools());
    }

    public final static List<PatternDto> toPatternDtos(List<Pattern> patterns){
        return patterns.stream().map(o-> toPatternDto(o)).collect(Collectors.toList());
    }

    public final static PhysicalDto toPhysicalDto(Physical physical){
        return new PhysicalDto(physical.getId(), physical.getUser().getId(),  physical.getCraft().getId(),
                physical.getSubcategory().getId(),
                physical.getTitle(), physical.getDescription(), physical.getPrice(), physical.getActive(),
                physical.getAmount(), physical.getSize(), physical.getColor(), physical.getDetails());
    }

    public final static List<PhysicalDto> toPhysicalDtos(List<Physical> physicals){
        return physicals.stream().map(o-> toPhysicalDto(o)).collect(Collectors.toList());
    }



    /* Convert DTOs to their Entities */

    /*
    public final static Pattern toPattern(PatternDto patternDto){
        return new Pattern(toUser(patternDto.getUserId()), toCraft(patternDto.getCraftId()),
                toSubcategory(patternDto.getSubcategoryId()),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                null, patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(),
                patternDto.getSizing(), patternDto.getDifficultyLevel(), patternDto.getTime());
    }

    public final static PhysicalDto toPhysicalDto(Physical physical){
        return new PhysicalDto(physical.getId(), toUserDto(physical.getUser()),  toCraftDto(physical.getCraft()),
                toSubcategoryDto(physical.getSubcategory()),
                physical.getTitle(), physical.getDescription(), physical.getPrice(), physical.getActive(),
                physical.getAmount(), physical.getSize(), physical.getColor(), physical.getDetails());
    }

    public final static Physical toPhysical(PhysicalDto physicalDto){
        return new Physical(physicalDto.getUserId(), toCraft(physicalDto.getCraftId()),
                toSubcategory(physicalDto.getSubcategoryId()),
                physicalDto.getTitle(), physicalDto.getDescription(), physicalDto.getPrice(), physicalDto.getActive(),
                null, physicalDto.getAmount(), physicalDto.getSize(), physicalDto.getColor(), physicalDto.getDetails());
    }*/
}
