package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.config.SecurityConfig;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@Import(SecurityConfig.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    // ── helpers ──────────────────────────────────────────────────────────

    private QuantityInputDTO buildInput(double v1, String u1, String t1,
                                        double v2, String u2, String t2) {
        return new QuantityInputDTO(new QuantityDTO(v1, u1, t1),
                                    new QuantityDTO(v2, u2, t2));
    }

    private QuantityMeasurementDTO compareResult(boolean result) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setOperation("compare");
        dto.setResultString(String.valueOf(result));
        return dto;
    }

    private QuantityMeasurementDTO valueResult(String op, double value, String unit, String type) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setOperation(op);
        dto.setResultValue(value);
        dto.setResultUnit(unit);
        dto.setResultMeasurementType(type);
        return dto;
    }

    // ── POST /compare ─────────────────────────────────────────────────────

    @Test
    void testCompare_ReturnsTrueForEqualQuantities() throws Exception {
        Mockito.when(service.compare(any(), any())).thenReturn(compareResult(true));

        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 12.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"))
                .andExpect(jsonPath("$.operation").value("compare"));
    }

    @Test
    void testCompare_ReturnsFalseForUnequalQuantities() throws Exception {
        Mockito.when(service.compare(any(), any())).thenReturn(compareResult(false));

        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 5.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("false"));
    }

    // ── POST /convert ─────────────────────────────────────────────────────

    @Test
    void testConvert_FeetToInch() throws Exception {
        Mockito.when(service.convert(any(), any()))
               .thenReturn(valueResult("convert", 12.0, "INCH", "LENGTH"));

        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 0.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(12.0))
                .andExpect(jsonPath("$.resultUnit").value("INCH"));
    }

    // ── POST /add ─────────────────────────────────────────────────────────

    @Test
    void testAdd_FeetAndInches_ReturnsSumInFeet() throws Exception {
        Mockito.when(service.add(any(), any()))
               .thenReturn(valueResult("add", 2.0, "FEET", "LENGTH"));

        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 12.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"));
    }

    // ── POST /subtract ────────────────────────────────────────────────────

    @Test
    void testSubtract_Weights() throws Exception {
        Mockito.when(service.subtract(any(), any()))
               .thenReturn(valueResult("subtract", 5.0, "KILOGRAM", "WEIGHT"));

        QuantityInputDTO input = buildInput(10.0, "KILOGRAM", "WEIGHT", 5.0, "KILOGRAM", "WEIGHT");
        mockMvc.perform(post("/api/v1/quantities/subtract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(5.0));
    }

    // ── POST /divide ──────────────────────────────────────────────────────

    @Test
    void testDivide_Weights() throws Exception {
        Mockito.when(service.divide(any(), any()))
               .thenReturn(valueResult("divide", 2.0, null, null));

        QuantityInputDTO input = buildInput(10.0, "KILOGRAM", "WEIGHT", 5.0, "KILOGRAM", "WEIGHT");
        mockMvc.perform(post("/api/v1/quantities/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    // ── GET /history/operation/{op} ────────────────────────────────────────

    @Test
    void testGetHistoryByOperation_ReturnsListFromService() throws Exception {
        QuantityMeasurementDTO dto = compareResult(true);
        Mockito.when(service.getHistoryByOperation("compare")).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/quantities/history/operation/compare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("compare"));
    }

    @Test
    void testGetHistoryByOperation_EmptyList() throws Exception {
        Mockito.when(service.getHistoryByOperation("add")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/quantities/history/operation/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ── GET /history/type/{type} ───────────────────────────────────────────

    @Test
    void testGetHistoryByType_ReturnsListFromService() throws Exception {
        Mockito.when(service.getHistoryByType("LENGTH")).thenReturn(List.of(compareResult(true)));

        mockMvc.perform(get("/api/v1/quantities/history/type/LENGTH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("compare"));
    }

    // ── GET /history/errored ───────────────────────────────────────────────

    @Test
    void testGetErrorHistory_ReturnsErroredDTOs() throws Exception {
        QuantityMeasurementDTO errored = new QuantityMeasurementDTO();
        errored.setError(true);
        errored.setErrorMessage("Cannot add incompatible types");
        Mockito.when(service.getErrorHistory()).thenReturn(List.of(errored));

        mockMvc.perform(get("/api/v1/quantities/history/errored"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].error").value(true))
                .andExpect(jsonPath("$[0].errorMessage").value("Cannot add incompatible types"));
    }

    // ── GET /count/{operation} ─────────────────────────────────────────────

    @Test
    void testGetCountByOperation_ReturnsCount() throws Exception {
        Mockito.when(service.getCountByOperation("compare")).thenReturn(5L);

        mockMvc.perform(get("/api/v1/quantities/count/compare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));
    }

    // ── Validation: missing body fields ───────────────────────────────────

    @Test
    void testCompare_NullBody_Returns400() throws Exception {
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCompare_MissingUnit_Returns400() throws Exception {
        // unit is empty — should fail @NotEmpty validation
        QuantityInputDTO input = buildInput(1.0, "", "LENGTH", 12.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCompare_MissingValue_Returns400() throws Exception {
        // Pass null for value — Jackson serialises to missing field which fails @NotNull
        String body = """
                {
                  "thisQuantityDTO": {"unit":"FEET","measurementType":"LENGTH"},
                  "thatQuantityDTO": {"value":12.0,"unit":"INCH","measurementType":"LENGTH"}
                }
                """;
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }
}
