package com.endava.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends FileLoader {

    private final String EMPLOYEES = "/employees";
    private final String EMPLOYEE_ID = EMPLOYEES + "/{id}";
    private final String EMPLOYEE_FILE_PATH = "request_samples/employee/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenValid_GetId_thenReturns200()
            throws Exception {
        mockMvc.perform(get(EMPLOYEE_ID, 2))
                .andExpect(status().isOk());
    }

    @Test
    public void whenValid_GetAll_thenReturns200()
            throws Exception {
        mockMvc.perform(get(EMPLOYEES))
                .andExpect(status().is(200));
    }

    @Test
    public void whenNotUniquePhone_Post_thenReturns409() throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "phoneUniqueness.json");
        mockMvc.perform(post(EMPLOYEES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void whenNotUniqueEmail_Post_thenReturns409() throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "emailUniqueness.json");
        mockMvc.perform(post(EMPLOYEES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void whenValid_Post_thenReturns200() throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "whenValid_Put_thenReturns200.json");
        mockMvc.perform(post(EMPLOYEES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }

    @Test
    public void whenValid_Put_thenReturns200()
            throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "whenValid_Post_thenReturns200.json");
        mockMvc.perform(put(EMPLOYEE_ID, 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }

    @Test
    public void whenNotUniquePhone_Put_thenReturns409() throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "phoneUniqueness.json");
        mockMvc.perform(put(EMPLOYEE_ID, 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void whenNotUniqueEmail_Put_thenReturns409() throws Exception {
        String requestJson = loadJson(EMPLOYEE_FILE_PATH + "emailUniqueness.json");
        mockMvc.perform(put(EMPLOYEE_ID, 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void whenInvalid_Post_thenReturns400()
            throws Exception {
        mockMvc.perform(post(EMPLOYEES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadJson(EMPLOYEE_FILE_PATH + "employeeReject.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenNotValid_Put_thenReturns400()
            throws Exception {
        mockMvc.perform(put(EMPLOYEE_ID, 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadJson(EMPLOYEE_FILE_PATH + "employeeReject.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenValid_Delete_thenReturns204()
            throws Exception {
        mockMvc.perform(delete(EMPLOYEE_ID, 3))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenNotValid_DeleteId_thenReturns204()
            throws Exception {
        mockMvc.perform(delete(EMPLOYEE_ID, 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenNotValid_GetId_thenReturns404()
            throws Exception {
        mockMvc.perform(get(EMPLOYEE_ID, 999))
                .andExpect(status().isNotFound());
    }
}
