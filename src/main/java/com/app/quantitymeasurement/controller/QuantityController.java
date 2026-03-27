package com.app.quantity.controller;

import com.app.quantity.dto.InputDTO;
import com.app.quantity.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuantityController {

    @Autowired
    private QuantityService service;

    @PostMapping("/compare")
    public boolean compare(@RequestBody InputDTO[] data) {
        return service.compare(data[0], data[1]);
    }

    @PostMapping("/add")
    public double add(@RequestBody InputDTO[] data) {
        return service.add(data[0], data[1]);
    }

    @PostMapping("/convert/{target}")
    public double convert(@RequestBody InputDTO data, @PathVariable String target) {
        return service.convert(data, target);
    }
}
