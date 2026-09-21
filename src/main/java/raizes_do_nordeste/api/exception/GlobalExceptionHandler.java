package raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroResponse> tratarErroDeAutenticacao(
			IllegalArgumentException exception){
		
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				"AUTENTICACAO",
				exception.getMessage()
		);
		
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(erro);
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErroResponse> tratarRecursoEncontrado(
			NoSuchElementException exception){
		
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"RECURSO_NAO_ENCONTRADO",
				"Recurso não encontrado"
		);
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(erro);
	}
}
