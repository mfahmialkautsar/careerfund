package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.Institution;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping("/institutions")
    public ResponseEntity<ApiResponse<List<Institution>>> getInstitutions() {
        Page<Institution> institutions = institutionService.getInstitutions();
        return ResponseEntity.ok(ApiResponse.<List<Institution>>builder()
                .data(institutions.getContent())
                .totalElements(institutions.getTotalElements())
                .build());
    }
}
