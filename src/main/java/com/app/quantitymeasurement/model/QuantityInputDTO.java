package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.unit.IMeasurable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
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

    /**
     * Cross-field validator: ensures both unit names are valid for their
     * respective measurement types. Triggered automatically by @Valid.
     */
    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnits() {
        return isValidUnit(thisQuantityDTO) && isValidUnit(thatQuantityDTO);
    }

    private boolean isValidUnit(QuantityDTO dto) {
        if (dto == null || dto.getUnit() == null || dto.getMeasurementType() == null) {
            return true; // let @NotNull / @NotEmpty handle those
        }
        try {
            IMeasurable.getUnitInstance(dto.getMeasurementType(), dto.getUnit());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
