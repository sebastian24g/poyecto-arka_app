package com.arka_app.arka.application.dto.output;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean active;
}
