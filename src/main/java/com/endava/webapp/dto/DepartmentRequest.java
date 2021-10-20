package com.endava.webapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
public class DepartmentRequest {
    private int id;

    @NotBlank(message = "Department's name is mandatory.")
    private String name;

    @NotBlank(message = "Department's location is mandatory.")
    private String location;
}
