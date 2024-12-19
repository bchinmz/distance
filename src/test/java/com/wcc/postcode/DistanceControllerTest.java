package com.wcc.postcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.postcode.distance.DistanceController;
import com.wcc.postcode.distance.model.DistanceCalcResponse;
import com.wcc.postcode.distance.model.PostCodeDetail;
import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import com.wcc.postcode.services.DistanceCalculator;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistanceController.class)
class DistanceControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DistanceCalculator distanceCalculator;

    @MockitoBean
    private UKPostCodeRepository repository;

    @Test
    void calcDistanceTest() throws Exception {
        when(repository.findById("AB10 1XG")).thenReturn(
                Optional.of(new UKPostCodeEntity(1, "AB10 1XG", 57.144165, -2.114848))
        );
        when(repository.findById("AB10 6RN")).thenReturn(
                Optional.of(new UKPostCodeEntity(2, "AB10 6RN", 57.137880, -2.121487))
        );
        when(distanceCalculator.calculate(any(), any())).thenReturn(5.0);
        var response = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/distances/calc")
                        .queryParams(MultiValueMap.fromSingleValue(
                                Map.of(
                                        "start", "AB10 1XG",
                                        "end", "AB10 6RN"
                                )
                        ))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var actual  = objectMapper.readValue(response.getContentAsString(), DistanceCalcResponse.class);
        assertThat(actual.getStart())
                .extracting(PostCodeDetail::getPostCode, PostCodeDetail::getLatitude, PostCodeDetail::getLongitude)
                .contains("AB10 1XG", "57° 8' 38.9940\"", "-2° 6' 53.4528\"");

        assertThat(actual.getEnd())
                .extracting(PostCodeDetail::getPostCode, PostCodeDetail::getLatitude, PostCodeDetail::getLongitude)
                .contains("AB10 6RN", "57° 8' 16.3680\"", "-2° 7' 17.3532\"");

        assertThat(actual.getDistance()).isEqualTo(5.0);
        assertThat(actual.getUnit()).isEqualTo("km");
    }

    @Test
    void missingParamTest() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/distances/calc")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidPostCodesTest() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/distances/calc")
                                .queryParams(MultiValueMap.fromSingleValue(
                                        Map.of(
                                                "start", "AB10 1XG",
                                                "end", "AB10 6RN"
                                        )
                                ))
                )
                .andExpect(status().isBadRequest());
    }
}
