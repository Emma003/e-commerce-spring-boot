package com.jtspringproject.JtSpringProject.integration_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jtspringproject.JtSpringProject.controller.AdminController;
import com.jtspringproject.JtSpringProject.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getReturnIndexAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/logout")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"));
    }

    @Test
    public void getIndexAPI() throws Exception
    {
        AdminController.usernameforclass = "user123";
        mvc.perform(get("/index")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("username", "user123"));
    }

    @Test
    public void getUserlogAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/userloginvalidate")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"));
    }

    @Test
    public void getAdminLoginAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/admin")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"));
    }

    @Test
    public void getAdminHomeAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/adminhome")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    public void getAdminLogAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/loginvalidate")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"));
    }

    @Test
    public void getAdminloginAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/loginvalidate")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"));
    }

    @Test
    public void getCategoryAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get("/admin/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("categories"));
    }

    @Test
    public void testUserLoginSuccess() throws Exception {
        String username = "john.doe";
        String password = "password123";

        mvc.perform(MockMvcRequestBuilders
                        .post("/userloginvalidate")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"));
    }



    ////////////////////////// DATABASE INTERACTION ////////////////////////////


}

