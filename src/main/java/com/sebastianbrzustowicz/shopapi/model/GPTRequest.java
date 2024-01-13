package com.sebastianbrzustowicz.shopapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GPTRequest {

    private String requestType;
    private String clientRequest;

}
