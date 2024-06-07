package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

@Entity
public class Step {

    private Long id;
    private Section section;
    private String rowNumber;
    private String instructions;

    private int stepOrder;

    public Step() {}


    public Step(String rowNumber, String instructions, int stepOrder) {
        this.rowNumber = rowNumber;
        this.instructions = instructions;
        this.stepOrder = stepOrder;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional=false, fetch= FetchType.LAZY)
    @JoinColumn(name= "sectionId")
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }
}
