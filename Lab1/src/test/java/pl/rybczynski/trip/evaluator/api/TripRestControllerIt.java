package pl.rybczynski.trip.evaluator.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TripRestControllerIt {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/trip/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world"));
    }

    @Test
    void shouldAddReview() throws Exception {
        MockHttpServletRequestBuilder builder1 =
                MockMvcRequestBuilders.post("/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"test\", \"destination\" : \"usa\", \"reviews\": [ { \"content\": \"nice trip\", \"user\": { \"name\": \"tomasz\", \"yearOfBirth\": \"2000-04-04\" }, \"rating\": 20 } ], \"price\": 20.0 }");

        MockHttpServletRequestBuilder builder2 =
                MockMvcRequestBuilders.put("/trip?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ \"content\": \"this trip was bussin\", \"user\": { \"name\": \"wiola\", \"yearOfBirth\": \"1969-01-01\" }, \"rating\": 20 }");

        mockMvc.perform(builder1)
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(builder2)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    void shouldAddTrip() throws Exception {
        MockHttpServletRequestBuilder builder1 =
                MockMvcRequestBuilders.post("/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"test\", \"destination\" : \"usa\", \"reviews\": [ { \"content\": \"nice trip\", \"user\": { \"name\": \"tomasz\", \"yearOfBirth\": \"2000-04-04\" }, \"rating\": 20 } ], \"price\": 20.0 }");

        mockMvc.perform(builder1)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
