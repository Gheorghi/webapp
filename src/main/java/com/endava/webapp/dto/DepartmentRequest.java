package com.endava.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentRequest {
    private int id;

    @NotBlank(message = "Department's name is mandatory.")
    private String name;

    @NotBlank(message = "Department's location is mandatory.")
    private String location;
}
