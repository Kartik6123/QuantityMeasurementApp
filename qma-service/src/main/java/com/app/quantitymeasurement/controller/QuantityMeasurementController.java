package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("Compare request received");
        return ResponseEntity.ok(service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("Convert request received");
        return ResponseEntity.ok(service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("Add request received");
        return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("Subtract request received");
        return ResponseEntity.ok(service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(@Valid @RequestBody QuantityInputDTO input) {
        logger.info("Divide request received");
        return ResponseEntity.ok(service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByOperation(@PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByType(@PathVariable String measurementType) {
        return ResponseEntity.ok(service.getHistoryByType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get all errored operations")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of operations by type")
    public ResponseEntity<Long> getCountByOperation(@PathVariable String operation) {
        return ResponseEntity.ok(service.getCountByOperation(operation));
    }
}
