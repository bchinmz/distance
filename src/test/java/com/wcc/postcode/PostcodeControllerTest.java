package com.wcc.postcode;

import com.wcc.postcode.management.PostcodeController;
import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostcodeController.class)
class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UKPostCodeRepository repository;

    @Test
    void retrievePostcodeDetail() throws Exception {
        when(repository.findById("test")).thenReturn(Optional.of(
                new UKPostCodeEntity(1, "test", 1.0, 2.0)
        ));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/postcodes/test")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        {"postcode":"test","latitude":1.0,"longitude":2.0}"""));
    }

    @Test
    void retrieveInvalidPostcodeDetail() throws Exception {
        when(repository.findById("test")).thenReturn(Optional.of(
                new UKPostCodeEntity(1, "test", 1.0, 2.0)
        ));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/postcodes/unknown")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePostcodeCoordinate() throws Exception {
        when(repository.findById("test")).thenReturn(Optional.of(
                new UKPostCodeEntity(1, "test", 1.0, 2.0)
        ));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/postcodes/test")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        	"latitude": 5,
                                        	"longitude": -1
                                        }""")
                )
                .andExpect(status().isNoContent());

        var argument = ArgumentCaptor.forClass(UKPostCodeEntity.class);
        verify(repository).save(argument.capture());
        assertThat(argument.getValue())
                .extracting(UKPostCodeEntity::getPostcode, UKPostCodeEntity::getLatitude, UKPostCodeEntity::getLongitude)
                .contains("test", 5.0, -1.0);
    }

    @Test
    void updateInvalidPostcodeCoordinate() throws Exception {
        when(repository.findById("test")).thenReturn(Optional.of(
                new UKPostCodeEntity(1, "test", 1.0, 2.0)
        ));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/postcodes/unknown")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        	"latitude": 1,
                                        	"longitude": 1
                                        }""")
                )
                .andExpect(status().isNotFound());
    }

}
