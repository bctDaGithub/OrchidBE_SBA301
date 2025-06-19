package org.example.orchidbe.query.controllers;

import org.example.orchidbe.query.documents.OrchidDocument;
import org.example.orchidbe.query.services.implement.OrchidQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("${api.query-path}/orchid")
public class OrchidQueryController {

    private final OrchidQueryService orchidQueryService;

    @Autowired
    public OrchidQueryController(OrchidQueryService orchidQueryService) {
        this.orchidQueryService = orchidQueryService;
    }

    @GetMapping("/{id}")
    public OrchidDocument getOrchidById(@PathVariable Long id) {
        return orchidQueryService.findByOrchidId(id);
    }

    @GetMapping
    public List<OrchidDocument> getAllOrchids() {
        return orchidQueryService.findAll();
    }
}