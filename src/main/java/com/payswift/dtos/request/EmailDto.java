package com.payswift.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
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

