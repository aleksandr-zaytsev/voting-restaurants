package ru.azaytsev.votingrestaurants.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.azaytsev.votingrestaurants.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.azaytsev.votingrestaurants.web.RestaurantController.REST_URL;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }
}