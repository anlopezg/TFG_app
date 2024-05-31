package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Step;
import jakarta.validation.constraints.NotNull;

public class StepDto {

    private String rowNumber;
    private String instructions;

    public StepDto() {
    }

    public StepDto(String rowNumber, String instructions) {

        this.rowNumber = rowNumber;
        this.instructions = instructions;
    }

    /**
     * Conversor
     * @param step Step
     * @return StepDto
     */
    public static StepDto toStepDto(Step step){
        return new StepDto(step.getRowNumber(), step.getInstructions());
    }


    @NotNull
    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    @NotNull
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
