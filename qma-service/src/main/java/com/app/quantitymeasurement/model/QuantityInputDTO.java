package com.app.quantitymeasurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    @NotNull(message = "First quantity cannot be null")
    @Valid
    private QuantityDTO thisQuantityDTO;

    @NotNull(message = "Second quantity cannot be null")
    @Valid
    private QuantityDTO thatQuantityDTO;
}
