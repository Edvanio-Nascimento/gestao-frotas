package com.edy.gestao_frotas.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(

        int status,
        String mensagem,
        LocalDateTime timestamp

) {
}
