package com.endava.webapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DepartmentResponse {
    private int id;
    private String name;
    private String location;
}
