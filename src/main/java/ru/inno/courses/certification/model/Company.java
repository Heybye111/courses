package ru.inno.courses.certification.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Company(String name, int id, boolean isActive) {
}




