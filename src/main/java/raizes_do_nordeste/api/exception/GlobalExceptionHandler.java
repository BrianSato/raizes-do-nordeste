package raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErroResponse> tratarRegraDeNegocio(
			IllegalStateException exception){
		
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				HttpStatus.CONFLICT.value(),
				"REGRA_DE_NEGOCIO",
				exception.getMessage()
		);
		
		return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
				
	}
}
