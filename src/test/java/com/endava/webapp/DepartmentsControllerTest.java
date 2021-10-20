package com.endava.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentsControllerTest extends FileLoader {

    private final String DEPARTMENTS = "/departments";
    private final String DEPARTMENT_ID = DEPARTMENTS + "/{id}";
    private final String DEPARTMENT_FILE_PATH = "request_samples/department/";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenValid_Post_thenReturns200() throws Exception {
        String requestJson = loadJson(DEPARTMENT_FILE_PATH + "departmentsNew.json");
        mockMvc.perform(post(DEPARTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }

    @Test
    public void whenInvalid_Post_thenReturns400()
            throws Exception {
        mockMvc.perform(post(DEPARTMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadJson(DEPARTMENT_FILE_PATH + "departmentRejects.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNotValid_Put_thenReturns400()
            throws Exception {
        mockMvc.perform(put(DEPARTMENT_ID, 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadJson(DEPARTMENT_FILE_PATH + "departmentRejects.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenValid_Put_thenReturns200()
            throws Exception {
        String requestJson = loadJson(DEPARTMENT_FILE_PATH + "departmentsNew.json");
        mockMvc.perform(put(DEPARTMENT_ID, 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }

    @Test
    public void whenValid_Delete_thenReturns204()
            throws Exception {
        mockMvc.perform(delete(DEPARTMENT_ID, 8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenNotValid_DeleteId_thenReturns404()
            throws Exception {
        mockMvc.perform(delete(DEPARTMENT_ID, 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenValid_GetId_thenReturns200()
            throws Exception {
        mockMvc.perform(get(DEPARTMENT_ID, 6))
                .andExpect(status().isOk());
    }

    @Test
    public void whenNotValid_GetId_thenReturns404()
            throws Exception {
        mockMvc.perform(get(DEPARTMENT_ID, 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenValid_GetAll_thenReturns200()
            throws Exception {
        mockMvc.perform(get(DEPARTMENTS))
                .andExpect(status().isOk());
    }
}
