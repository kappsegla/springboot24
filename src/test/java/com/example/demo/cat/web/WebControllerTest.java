package com.example.demo.cat.web;

import com.example.demo.cat.CatService;
import com.example.demo.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebController.class)
@Import(SecurityConfig.class) //test slices such as @WebMvcTest flat out exclude @configuration classes
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;

    //Test for method based security
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testCats() throws Exception {
        List<String> catNames = List.of("Tom", "Jerry", "Garfield");
        when(catService.allCatNames()).thenReturn(catNames);

        mockMvc.perform(MockMvcRequestBuilders.get("/web/cats"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("catNames", catNames))
                .andExpect(MockMvcResultMatchers.model().attribute("catCount", 3))
                .andExpect(MockMvcResultMatchers.view().name("cats"));
    }

    @Test
    void testWithFullSecurity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/cats")
                        .with(user("admin").password("admin").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void unauthenticatedHaveNoAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/cats"))
                .andExpect(status().isForbidden());
    }

}
