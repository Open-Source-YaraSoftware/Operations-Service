package com.workshopngine.platform.serviceoperations.operations.domain.model.entities;

import com.workshopngine.platform.serviceoperations.operations.domain.model.commands.CreateRecommendationCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "diagnostic_finding_id", nullable = false)
    private DiagnosticFinding diagnosticFinding;

    public Recommendation() {
    }

    public Recommendation(CreateRecommendationCommand command, DiagnosticFinding diagnosticFinding) {
        this.content = command.content();
        this.diagnosticFinding = diagnosticFinding;
    }
}
