package com.psc.cloud.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceAddress {
    private String composite;
    private String product;
    private String recommend;
    private String review;
}
