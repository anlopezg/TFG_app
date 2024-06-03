package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Pattern;

import java.util.List;
import java.util.stream.Collectors;

import static es.udc.paproject.backend.rest.dtos.ProductConversor.productImagesToStringList;
import static es.udc.paproject.backend.rest.dtos.SectionDto.toSectionDto;
import static es.udc.paproject.backend.rest.dtos.ToolDto.toToolDto;
import static es.udc.paproject.backend.rest.dtos.YarnDto.toYarnDto;

public class PatternConversor {

    /* Pattern Conversor */
    public final static PatternDto toPatternDto(Pattern pattern){

        return new PatternDto(pattern.getId(), pattern.getUser().getId(),  pattern.getCraft().getId(),
                pattern.getSubcategory().getId(),
                pattern.getTitle(), pattern.getDescription(), pattern.getPrice(), pattern.getActive(),
                pattern.getIntroduction(), pattern.getNotes(),
                pattern.getGauge(), pattern.getSizing(), pattern.getDifficultyLevel(), pattern.getTime(),
                pattern.getAbbreviations(), pattern.getSpecialAbbreviations(),
                productImagesToStringList(pattern));
    }

    public static PatternDto toPatternDtoFull(Pattern pattern){

        List<ToolDto> tools = pattern.getTools().stream().map(i-> toToolDto(i)).collect(Collectors.toList());
        List<YarnDto> yarns = pattern.getYarns().stream().map(i-> toYarnDto(i)).collect(Collectors.toList());
        List<SectionDto> sections = pattern.getSections().stream().map(i-> toSectionDto(i)).collect(Collectors.toList());

        return new PatternDto(pattern.getId(), pattern.getUser().getId(),  pattern.getCraft().getId(),
                pattern.getSubcategory().getId(),
                pattern.getTitle(), pattern.getDescription(), pattern.getPrice(), pattern.getActive(),
                pattern.getIntroduction(), pattern.getNotes(),
                pattern.getGauge(), pattern.getSizing(), pattern.getDifficultyLevel(), pattern.getTime(),
                pattern.getAbbreviations(), pattern.getSpecialAbbreviations(),
                productImagesToStringList(pattern), tools, yarns, sections);
    }


    public static PatternDto toFullPatternDto(Pattern pattern) {
        PatternDto dto = new PatternDto();
        dto.setId(pattern.getId());
        dto.setUserId(pattern.getUser().getId());
        dto.setCraftId(pattern.getCraft().getId());
        dto.setSubcategoryId(pattern.getSubcategory().getId());
        dto.setTitle(pattern.getTitle());
        dto.setDescription(pattern.getDescription());
        dto.setPrice(pattern.getPrice());
        dto.setActive(pattern.getActive());
        dto.setIntroduction(pattern.getIntroduction());
        dto.setNotes(pattern.getNotes());
        dto.setGauge(pattern.getGauge());
        dto.setSizing(pattern.getSizing());
        dto.setDifficultyLevel(pattern.getDifficultyLevel());
        dto.setTime(pattern.getTime());
        dto.setAbbreviations(pattern.getAbbreviations());
        dto.setSpecialAbbreviations(pattern.getSpecialAbbreviations());
        dto.setImagesUrl(productImagesToStringList(pattern));

        dto.setTools(pattern.getTools().stream().map(ToolDto::toToolDto).collect(Collectors.toList()));
        dto.setYarns(pattern.getYarns().stream().map(YarnDto::toYarnDto).collect(Collectors.toList()));
        dto.setSections(pattern.getSections().stream().map(SectionDto::toSectionDto).collect(Collectors.toList()));

        return dto;
    }

    public final static List<PatternDto> toPatternDtos(List<Pattern> patterns){
        return patterns.stream().map(o-> toPatternDtoFull(o)).collect(Collectors.toList());
    }
}
