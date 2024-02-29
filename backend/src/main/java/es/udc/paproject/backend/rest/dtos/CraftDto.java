package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.*;


public class CraftDto {

    public interface AllValidations {}
    private Long id;

    private String craftName;
    public CraftDto(){}

    public CraftDto(Long id, String craftName){
        this.id=id;
        this.craftName=craftName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(groups = {AllValidations.class})
    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
    }
}
