package com.application.school.exception.exceptionHndler;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private String message;
    private String method;
    private String requestURI;
    private Date timeStamp;
}
