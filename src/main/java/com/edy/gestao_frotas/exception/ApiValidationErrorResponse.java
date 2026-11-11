package com.edy.gestao_frotas.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiValidationErrorResponse(

        int status,
        Map<String, String> errors,
        LocalDateTime timestamp

) {
}
