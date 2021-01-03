package com.genius.demo.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

interface ApiSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ApiValidationError implements ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}