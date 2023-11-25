package com.payswift.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    @NotBlank(message = "to must not be empty")
    private String to;
    @NotBlank(message = "subject must not be empty")
    private String subject;
    @NotBlank(message = "content must not be empty")
    private String content;
}

