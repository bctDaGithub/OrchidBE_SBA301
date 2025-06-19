package org.example.orchidbe.command.controllers;

import org.example.orchidbe.command.dtos.OrchidEntityDTO;
import org.example.orchidbe.command.entities.OrchidEntity;
import org.example.orchidbe.command.services.define.IOrchidCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("${api.command-path}/orchid")
public class OrchidCommandController {

    @Autowired
    IOrchidCommandService orchidCommandService;

    @PostMapping
    public ResponseEntity<String> createOrchid(@RequestBody OrchidEntityDTO orchidEntityDTO){
        OrchidEntity newOrchid = new OrchidEntity(
                orchidEntityDTO.getOrchidName(),
                orchidEntityDTO.getOrchidDescription(),
                orchidEntityDTO.getPrice(),
                orchidEntityDTO.getOrchidUrl(),
                orchidEntityDTO.isNatural()
        );
        orchidCommandService.createOrchid(newOrchid);
        return ResponseEntity.ok("Orchid created successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrchid(@RequestBody OrchidEntityDTO orchidEntityDTO) {
        orchidCommandService.updateOrchid(
                orchidEntityDTO.getOrchidId(),
                orchidEntityDTO.getOrchidName(),
                orchidEntityDTO.getOrchidDescription(),
                orchidEntityDTO.getPrice(),
                orchidEntityDTO.getOrchidUrl(),
                orchidEntityDTO.isNatural()
        );
        return ResponseEntity.ok("Orchid updated successfully");
    }

    @PutMapping("/disable")
    public ResponseEntity<String> disableOrchid(@RequestBody OrchidEntityDTO orchidEntityDTO) {
        orchidCommandService.disableOrchid(orchidEntityDTO.getOrchidId());
        return ResponseEntity.ok("Orchid disabled successfully");
    }

    @PutMapping("/enable")
    public ResponseEntity<String> enableOrchid(@RequestBody OrchidEntityDTO orchidEntityDTO) {
        orchidCommandService.enableOrchid(orchidEntityDTO.getOrchidId());
        return ResponseEntity.ok("Orchid enabled successfully");
    }
}
