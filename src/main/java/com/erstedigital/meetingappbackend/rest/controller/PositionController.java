package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.response.PositionResponse;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/position")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public @ResponseBody
    List<PositionResponse> getAllPositions() {
        return positionService.getAll().stream().map(PositionResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    PositionResponse getPositionById(@PathVariable("id") Integer id) throws NotFoundException {
        return new PositionResponse(positionService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<PositionResponse> addNewPosition(@RequestBody PositionRequest body) {
        return new ResponseEntity<>(new PositionResponse(positionService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody PositionResponse updatePosition(@PathVariable("id") Integer id,
                                                         @RequestBody PositionRequest body) throws NotFoundException {
        return new PositionResponse(positionService.update(id, body));
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody void deletePosition(@RequestParam Integer id) throws NotFoundException {
        positionService.delete(id);
    }
}