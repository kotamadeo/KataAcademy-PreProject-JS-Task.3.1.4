package com.gmail.at.kotamadeo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfoException {
    private String informationMessage;
}
