package com.app.quantitymeasurement;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private QuantityInputDTO buildInput(double v1, String u1, String t1, double v2, String u2, String t2) {
        return new QuantityInputDTO(new QuantityDTO(v1, u1, t1), new QuantityDTO(v2, u2, t2));
    }

    @Test
    void contextLoads() {}

    @Test
    void testCompare_FeetAndInches_ReturnsTrue() throws Exception {
        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 12.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void testConvert_FeetToInch() throws Exception {
        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 0.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(12.0));
    }

    @Test
    void testAdd_FeetAndInches() throws Exception {
        QuantityInputDTO input = buildInput(1.0, "FEET", "LENGTH", 12.0, "INCH", "LENGTH");
        mockMvc.perform(post("/api/v1/quantities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    void testDivide_Weights() throws Exception {
        QuantityInputDTO input = buildInput(10.0, "KILOGRAM", "WEIGHT", 5.0, "KILOGRAM", "WEIGHT");
        mockMvc.perform(post("/api/v1/quantities/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    void testTemperatureCompare_CelsiusAndFahrenheit() throws Exception {
        QuantityInputDTO input = buildInput(0.0, "CELSIUS", "TEMPERATURE", 32.0, "FAHRENHEIT", "TEMPERATURE");
        mockMvc.perform(post("/api/v1/quantities/compare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void testGetHistoryByOperation() throws Exception {
        mockMvc.perform(get("/api/v1/quantities/history/operation/compare"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCountByOperation() throws Exception {
        mockMvc.perform(get("/api/v1/quantities/count/compare"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetErrorHistory() throws Exception {
        mockMvc.perform(get("/api/v1/quantities/history/errored"))
                .andExpect(status().isOk());
    }
}
