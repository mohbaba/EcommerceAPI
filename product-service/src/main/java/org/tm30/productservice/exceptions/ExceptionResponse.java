package org.tm30.productservice.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;


}
