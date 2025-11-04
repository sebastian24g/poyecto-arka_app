package com.arka_app.arka.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean active;
}
