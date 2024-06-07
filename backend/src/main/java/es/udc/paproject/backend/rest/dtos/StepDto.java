package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Step;
import jakarta.validation.constraints.NotNull;

public class StepDto {

    private String rowNumber;
    private String instructions;

    private int stepOrder;

    public StepDto() {
    }

    public StepDto(String rowNumber, String instructions, int stepOrder) {

        this.rowNumber = rowNumber;
        this.instructions = instructions;
        this.stepOrder = stepOrder;
    }

    /**
     * Conversor
     * @param step Step
     * @return StepDto
     */
    public static StepDto toStepDto(Step step){
        return new StepDto(step.getRowNumber(), step.getInstructions(), step.getStepOrder());
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

    @NotNull
    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }
}
