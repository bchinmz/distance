package com.wcc.postcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.postcode.distance.model.DistanceCalcResponse;
import com.wcc.postcode.distance.model.PostCodeDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DistanceControllerIntegrationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void calcDistance() throws Exception {
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

        assertThat(actual.getDistance()).isEqualTo(0.805504680324237);
        assertThat(actual.getUnit()).isEqualTo("km");

    }

}
