package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Pattern;
import es.udc.paproject.backend.model.entities.Section;
import es.udc.paproject.backend.model.entities.Tool;
import es.udc.paproject.backend.model.entities.Yarn;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.PatternService;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import es.udc.paproject.backend.rest.dtos.PatternDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.paproject.backend.rest.dtos.PatternConversor.*;
import static es.udc.paproject.backend.rest.dtos.SectionDto.toSections;
import static es.udc.paproject.backend.rest.dtos.ToolDto.toTools;
import static es.udc.paproject.backend.rest.dtos.YarnDto.toYarns;

@RestController
@RequestMapping("/patterns")
public class PatternController {


    @Autowired
    private PatternService patternService;

    @PostMapping("/create")
    public ResponseEntity<PatternDto> createPattern(@RequestAttribute Long userId,
                                                    @Validated({PatternDto.AllValidations.class}) @RequestBody PatternDto patternDto) throws InstanceNotFoundException, PermissionException, UserNotSellerException, MaxItemsExceededException {

        if(!userId.equals(patternDto.getUserId())){
            throw new PermissionException();
        }

        List<Tool> tools = toTools(patternDto.getTools());
        List<Yarn> yarns = toYarns(patternDto.getYarns());
        List<Section> sections = toSections(patternDto.getSections());

        Pattern createdPattern = patternService.createPattern(patternDto.getUserId(), patternDto.getCraftId(), patternDto.getSubcategoryId(),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(), patternDto.getSizing(),
                patternDto.getDifficultyLevel(), patternDto.getTime(),
                patternDto.getAbbreviations(), patternDto.getSpecialAbbreviations(),
                patternDto.getImagesUrl(), tools, yarns, sections);

        PatternDto createdPatternDto = toPatternDtoFull(createdPattern);

        return new ResponseEntity<>(createdPatternDto, HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public BlockDto<PatternDto> findAddedPatterns(@RequestAttribute Long userId, @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException, UserNotSellerException {

        Block<Pattern> patternBlock = patternService.findAddedPatterns(userId, page,6 );

        return new BlockDto<>(toPatternDtos(patternBlock.getItems()), patternBlock.getExistMoreItems());
    }

    @GetMapping("/find/{id}")
    public PatternDto findPatternById(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException, UserNotOwnerException, PermissionException {

        return toFullPatternDto(patternService.findPatternById(userId, id));
    }

    @PutMapping("/edit/{id}")
    public PatternDto editPattern(@RequestAttribute Long userId,  @PathVariable Long id,  @Validated({PatternDto.AllValidations.class}) @RequestBody PatternDto patternDto) throws InstanceNotFoundException, UserNotOwnerException, PermissionException, MaxItemsExceededException {

        if (!userId.equals(patternDto.getUserId())) {
            throw new PermissionException();
        }

        List<Tool> tools = toTools(patternDto.getTools());
        List<Yarn> yarns = toYarns(patternDto.getYarns());
        List<Section> sections = toSections(patternDto.getSections());

        Pattern updatedPattern = patternService.editPattern(id, userId, patternDto.getCraftId(), patternDto.getSubcategoryId(),
                patternDto.getTitle(), patternDto.getDescription(), patternDto.getPrice(), patternDto.getActive(),
                patternDto.getIntroduction(), patternDto.getNotes(), patternDto.getGauge(), patternDto.getSizing(),
                patternDto.getDifficultyLevel(), patternDto.getTime(), patternDto.getAbbreviations(), patternDto.getSpecialAbbreviations(),
                patternDto.getImagesUrl(), tools, yarns, sections);

        return toPatternDtoFull(updatedPattern);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePattern(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException, PermissionException {

        patternService.deletePattern(userId, id);

    }

    @GetMapping("/purchased/{id}")
    public PatternDto getPurchasedPatternById(@RequestAttribute Long userId, @PathVariable Long id) throws InstanceNotFoundException, PermissionException {
        Pattern pattern = patternService.findPurchasedPatternById(userId, id);
        return toPatternDtoFull(pattern);
    }

    @GetMapping("/purchased")
    public List<PatternDto> getPurchasedPatterns(@RequestAttribute Long userId) throws InstanceNotFoundException {
        List<Pattern> patterns = patternService.findPurchasedPatterns(userId);
        return toPatternDtos(patterns);
    }

}
