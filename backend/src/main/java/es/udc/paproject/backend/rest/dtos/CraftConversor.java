package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Craft;

import java.util.List;
import java.util.stream.Collectors;


public class CraftConversor {

    private CraftConversor(){}

    public final static CraftDto toCraftDto(Craft craft){
        return new CraftDto(craft.getId(), craft.getCraftName());
    }

    public final static List<CraftDto> toCraftDtos(List<Craft> crafts){
        return crafts.stream().map(c-> toCraftDto(c)).collect(Collectors.toList());
    }

    public final static Craft toCraft(CraftDto craftDto){
        return new Craft(craftDto.getCraftName());
    }
}
