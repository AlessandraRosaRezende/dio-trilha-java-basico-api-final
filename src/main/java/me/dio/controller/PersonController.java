package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.controller.dto.PersonDTO;
import me.dio.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/persons")
@Tag(name = "Person Controller", description = "RESTful API for managing persons.")
public record PersonController (PersonService personService) {
    @GetMapping
    @Operation(summary = "Get all persons", description = "Retrieve a list of all registered persons")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<PersonDTO>> findAll() {
        var persons = personService.findAll();
        var personsDto = persons.stream().map(PersonDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(personsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID", description = "Retrieve a specific person based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        var person = personService.findById(id);
        return ResponseEntity.ok(new PersonDTO(person));
    }

    @PostMapping
    @Operation(summary = "Create a new person", description = "Create a new person and return the created person's data")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Person created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        var person = personService.create(personDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        return ResponseEntity.created(location).body(new PersonDTO(person));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person", description = "Update the data of an existing person based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Person updated successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "422", description = "Invalid person data provided")
    })
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        var person = personService.update(id, personDTO.toModel());
        return ResponseEntity.ok(new PersonDTO(person));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person", description = "Delete an existing person based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
