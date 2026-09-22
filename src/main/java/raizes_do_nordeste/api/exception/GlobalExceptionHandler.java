package raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/* 409 CONFLIT
	 * Usada quando a operação viola uma regra de negócio
	 * ou quando o estado atual do recurso não permite a operação.
	 */
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
	/* 400 BAD REQUEST
	 * Usada quando os dados enviados pelo cliente são inválidos
	 * ou não atendem às regras básicas de validação.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroResponse> tratarErroValidacao(
			IllegalArgumentException exception){
		
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"VALIDACAO",
				exception.getMessage()
		);
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(erro);
	}
	/* 404 NOT FOUND
	 * Usada quando o recurso solicitado não existe no banco de dados.
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
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
	/* 404 BAD REQUEST
	 * Usada quando a requisição não atende às validações
	 * definidas nos DTOs, como @NotNull, @NotBlank, etc.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroResponse> tratarErroDeValidacao(
			MethodArgumentNotValidException exception){
		
		String mensagem = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.findFirst()
				.orElse("Dados inválidos");
		
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"VALIDACAO",
				mensagem
		);
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(erro);		
	}
	/* 401 UNAUTHORIZED:
	 * Usada quando o usuário não consegue se autenticar,
	 * por exemplo, quando informa uma senha inválida.
	 */
	@ExceptionHandler(AutenticacaoException.class)
	public ResponseEntity<ErroResponse> tratarErroDeAutenticacao(
			AutenticacaoException exception){
		
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
}
