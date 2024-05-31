package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Tool;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ToolDto {

    private String toolName;
    private int amount;

    public ToolDto() {
    }

    public ToolDto(String toolName, int amount) {
        this.toolName = toolName;
        this.amount = amount;
    }

    public static ToolDto toToolDto(Tool tool){
        return new ToolDto(tool.getToolName(), tool.getAmount());
    }

    /**
     * Conversor from List of ToolDto to Tool
     * @param toolsDto List of ToolDTO
     * @return List of Tool
     */
    public static List<Tool> toTools(List<ToolDto> toolsDto){
        return toolsDto.stream().map(dto -> new Tool(dto.toolName, dto.getAmount())).collect(Collectors.toList());
    }

    @NotNull
    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    @NotNull
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
