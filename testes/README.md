# Plano de Testes — Raízes do Nordeste

## 1. Objetivo

Este documento apresenta o plano de testes da API **Raízes do Nordeste**, desenvolvida em Java com Spring Boot, com persistência de dados em MySQL.

Os testes foram realizados utilizando a coleção **Postman**, disponibilizada neste diretório, com o objetivo de validar:

* autenticação e autorização;
* validação dos dados de entrada;
* regras de negócio;
* criação de pedidos;
* validação de disponibilidade e estoque;
* processamento de pagamento mock;
* atualização do status dos pedidos;
* tratamento padronizado de erros.

A coleção de testes está disponível em:

```text
testes/Postman/Raizes-do-Nordeste.postman_collection.json
```

---

## 2. Tecnologias e pré-requisitos

Para executar a API e reproduzir os testes, é necessário possuir:

* Java 17 ou superior;
* Maven;
* MySQL;
* Postman;
* Git (opcional, caso o projeto seja clonado do GitHub).

A aplicação utiliza Spring Boot e o banco de dados MySQL para persistência dos dados.

---

## 3. Fluxo principal validado

O fluxo principal escolhido para o MVP foi:

```text
Criação do Pedido
       ↓
Pagamento Mock
       ↓
Atualização do Status
```

O fluxo contempla:

1. criação de um pedido;
2. validação dos produtos e da disponibilidade na unidade;
3. persistência do pedido no banco de dados;
4. processamento de pagamento mock;
5. atualização do status do pedido quando o pagamento é aprovado;
6. manutenção do pedido em `AGUARDANDO_PAGAMENTO` quando o pagamento é recusado.

---

## 4. Execução da aplicação

Antes de executar os testes, o MySQL deve estar disponível e o banco de dados configurado conforme as propriedades da aplicação.

Banco utilizado:

```text
raizes_nordeste
```

Após iniciar a aplicação Spring Boot, a API estará disponível em:

```text
http://localhost:8080
```

A aplicação deve permanecer em execução durante os testes realizados pelo Postman.
## 5. Dados utilizados nos testes

Os testes foram realizados utilizando dados previamente cadastrados no banco de dados.

### Produto

| Campo    | Valor             |
| -------- | ----------------- |
| ID       | 1                 |
| Nome     | Coxinha de Frango |
| Preço    | R$ 8,50           |
| Validade | 31/12/2026        |

### Unidade

| Campo    | Valor              |
| -------- | ------------------ |
| ID       | 1                  |
| Nome     | Unidade Centro     |
| Endereço | Rua Principal, 100 |

### Usuário

| Campo  | Valor             |
| ------ | ----------------- |
| ID     | 1                 |
| E-mail | `teste@teste.com` |
| Senha  | `123456`          |

O usuário possui perfil adequado para a execução dos testes de pedidos e pagamentos.

### Estoque

Para os testes de validação de estoque, foi utilizado:

| Produto           | Unidade        | Quantidade |
| ----------------- | -------------- | ---------: |
| Coxinha de Frango | Unidade Centro |          2 |

Essa quantidade permite testar tanto uma solicitação válida quanto uma solicitação com estoque insuficiente.

---

## 6. Configuração do Postman

A coleção de testes utiliza a variável:

```text
baseUrl = http://localhost:8080
```

Também é utilizada a variável:

```text
token
```

A variável `token` inicialmente deve estar vazia.

### Obtenção do token

O teste **T01 - Login válido** realiza a autenticação através do endpoint:

```text
POST /auth
```

com as credenciais:

```json
{
    "email": "teste@teste.com",
    "senha": "123456"
}
```

Após uma autenticação bem-sucedida, o script de pós-resposta do Postman armazena automaticamente o JWT recebido na variável de ambiente `token`.

Os demais testes autenticados utilizam essa variável através do cabeçalho:

```text
Authorization: Bearer {{token}}
```

> **Importante:** não é necessário copiar manualmente o JWT para os demais testes. O token é obtido automaticamente pelo T01.

---

## 7. Ordem recomendada para execução

Para reproduzir os testes, recomenda-se executar as requisições na seguinte ordem:

### Autenticação

1. **T01 - Login válido**
2. **T02 - Acesso sem token**
3. **T03 - Perfil sem permissão**

### Validações e erros

4. **T04 - Campo obrigatório ausente**
5. **T05 - Quantidade negativa**
6. **T06 - Produto inexistente**
7. **T07 - Unidade inexistente**
8. **T08 - Estoque insuficiente**

### Pagamento aprovado

9. **T09.1 - Criar pedido para pagamento**
10. **T09.2 - Pagamento aprovado**
11. **T09.3 - Confirmar status do pedido**

### Pagamento recusado

12. **T10.1 - Criar pedido para pagamento recusado**
13. **T10.2 - Pagamento recusado**
14. **T10.3 - Confirmar status após pagamento recusado**

Os testes T09 e T10 possuem mais de uma requisição porque representam fluxos completos, nos quais o resultado de uma etapa é utilizado na etapa seguinte.
## 8. Cenários de teste

Os cenários abaixo foram executados utilizando a coleção Postman disponibilizada no diretório `testes/Postman`.

### T01 — Login válido

**Endpoint:** `POST /auth`

**Pré-condição:** usuário previamente cadastrado.

**Entrada:**

```json
{
    "email": "teste@teste.com",
    "senha": "123456"
}
```

**Resultado esperado:** `200 OK`, com retorno dos dados do usuário e um token JWT válido.

**Evidência:** requisição `T01 - Login válido` na pasta `Auth`.

---

### T02 — Acesso sem token

**Endpoint:** `GET /pedidos/{id}`

**Pré-condição:** não enviar o cabeçalho `Authorization`.

**Entrada:** ID de um pedido existente.

**Resultado esperado:** `401 Unauthorized`.

**Evidência:** requisição `T02 - Acesso sem token` na pasta `Auth`.

---

### T03 — Perfil sem permissão

**Endpoint:** `POST /fidelidade/{id}/pontos`

**Pré-condição:** usuário autenticado com perfil sem permissão para acessar o recurso.

**Entrada:** ID de uma fidelização e quantidade de pontos.

**Resultado esperado:** `403 Forbidden`.

**Evidência:** requisição `T03 - Perfil sem permissão` na pasta `Auth`.

---

### T04 — Campo obrigatório ausente

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado.

**Entrada:** requisição sem o campo obrigatório `canalPedido`.

**Resultado esperado:** `400 Bad Request`, com mensagem informando que `canalPedido` é obrigatório.

**Evidência:** requisição `T04 - Campo obrigatório ausente` na pasta `Erros`.

---

### T05 — Quantidade negativa

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado e produto existente.

**Entrada:**

```json
{
    "canalPedido": "TOTEM",
    "unidadeId": 1,
    "itens": [
        {
            "produtoId": 1,
            "quantidade": -1
        }
    ]
}
```

**Resultado esperado:** `400 Bad Request`, com mensagem indicando que a quantidade deve ser maior que zero.

**Evidência:** requisição `T05 - Quantidade negativa` na pasta `Erros`.

---

### T06 — Produto inexistente

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado e unidade existente.

**Entrada:** pedido contendo um `produtoId` inexistente.

**Resultado esperado:** `404 Not Found`, com resposta JSON padronizada.

**Evidência:** requisição `T06 - Produto inexistente` na pasta `Produtos`.

---

### T07 — Unidade inexistente

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado e produto existente.

**Entrada:** pedido contendo uma `unidadeId` inexistente.

**Resultado esperado:** `404 Not Found`, com resposta JSON padronizada.

**Evidência:** requisição `T07 - Unidade inexistente` na pasta `Erros`.

---

### T08 — Estoque insuficiente

**Endpoint:** `POST /pedidos`

**Pré-condição:** produto existente, unidade existente e estoque disponível igual a 2 unidades.

**Entrada:** pedido solicitando quantidade superior ao estoque disponível.

**Resultado esperado:** `409 Conflict`, com mensagem:

```text
Estoque insuficiente para o produto: Coxinha de Frango
```

**Evidência:** requisição `T08 - Estoque insuficiente` na pasta `Erros`.

---

### T09 — Pagamento aprovado

O cenário T09 representa o fluxo principal com pagamento aprovado.

#### T09.1 — Criar pedido para pagamento

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado, produto e unidade existentes e estoque disponível.

**Entrada:** pedido válido contendo canal, unidade e item.

**Resultado esperado:** `201 Created`, com o pedido criado e status inicial `AGUARDANDO_PAGAMENTO`.

**Evidência:** requisição `T09.1 - Criar pedido para pagamento` na pasta `Pedidos`.

#### T09.2 — Pagamento aprovado

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T09.1.

**Entrada:** pagamento mock com aprovação.

**Resultado esperado:** `200 OK`, pagamento com status `APROVADO`.

**Evidência:** requisição `T09.2 - Pagamento aprovado` na pasta `Pagamento`.

#### T09.3 — Confirmar status do pedido

**Endpoint:** `GET /pedidos/{id}`

**Pré-condição:** pagamento do pedido aprovado no T09.2.

**Entrada:** ID do pedido criado no T09.1.

**Resultado esperado:** `200 OK`, com o pedido apresentando status `PAGAMENTO_APROVADO`.

**Evidência:** requisição `T09.3 - Confirmar status do pedido` na pasta `Pedidos`.

---

### T10 — Pagamento recusado

O cenário T10 representa o comportamento da API quando o pagamento mock é recusado.

#### T10.1 — Criar pedido para pagamento recusado

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado, produto e unidade existentes e estoque disponível.

**Entrada:** pedido válido.

**Resultado esperado:** `201 Created`, com status inicial `AGUARDANDO_PAGAMENTO`.

**Evidência:** requisição `T10.1 - Criar pedido para pagamento recusado` na pasta `Pedidos`.

#### T10.2 — Pagamento recusado

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T10.1.

**Entrada:** pagamento mock com aprovação definida como `false`.

**Resultado esperado:** `200 OK`, pagamento com status `RECUSADO`.

**Evidência:** requisição `T10.2 - Pagamento recusado` na pasta `Pagamento`.

#### T10.3 — Confirmar status após pagamento recusado

**Endpoint:** `GET /pedidos/{id}`

**Pré-condição:** pagamento recusado no T10.2.

**Entrada:** ID do pedido criado no T10.1.

**Resultado esperado:** `200 OK`, mantendo o pedido no status `AGUARDANDO_PAGAMENTO`.

**Evidência:** requisição `T10.3 - Confirmar status após pagamento recusado` na pasta `Pedidos`.

---

## 9. Resumo dos cenários

| ID    | Tipo     | Método | Endpoint                  | Resultado esperado |
| ----- | -------- | ------ | ------------------------- | ------------------ |
| T01   | Positivo | POST   | `/auth`                   | 200                |
| T02   | Negativo | GET    | `/pedidos/{id}`           | 401                |
| T03   | Negativo | POST   | `/fidelidade/{id}/pontos` | 403                |
| T04   | Negativo | POST   | `/pedidos`                | 400                |
| T05   | Negativo | POST   | `/pedidos`                | 400                |
| T06   | Negativo | POST   | `/pedidos`                | 404                |
| T07   | Negativo | POST   | `/pedidos`                | 404                |
| T08   | Negativo | POST   | `/pedidos`                | 409                |
| T09.1 | Positivo | POST   | `/pedidos`                | 201                |
| T09.2 | Positivo | POST   | `/pagamentos`             | 200                |
| T09.3 | Positivo | GET    | `/pedidos/{id}`           | 200                |
| T10.1 | Positivo | POST   | `/pedidos`                | 201                |
| T10.2 | Positivo | POST   | `/pagamentos`             | 200                |
| T10.3 | Positivo | GET    | `/pedidos/{id}`           | 200                |

## 10. Padrão de respostas de erro

A API utiliza um padrão único para respostas de erro, facilitando a identificação do problema durante os testes.

Exemplo:

```json
{
    "timestamp": "2026-09-24T20:00:00",
    "status": 409,
    "erro": "REGRA_DE_NEGOCIO",
    "mensagem": "Estoque insuficiente para o produto: Coxinha de Frango"
}
```

Os principais códigos HTTP utilizados nos testes são:

| Código | Significado                               |
| ------ | ----------------------------------------- |
| 200    | Requisição processada com sucesso         |
| 201    | Recurso criado com sucesso                |
| 400    | Dados de entrada inválidos                |
| 401    | Usuário não autenticado                   |
| 403    | Usuário autenticado sem permissão         |
| 404    | Recurso não encontrado                    |
| 409    | Conflito ou regra de negócio não atendida |

---

## 11. Autenticação e autorização

A API utiliza autenticação baseada em **JWT (JSON Web Token)**.

O fluxo de autenticação utilizado nos testes é:

```text
Login
  ↓
POST /auth
  ↓
Token JWT
  ↓
Authorization: Bearer {{token}}
  ↓
Acesso aos recursos protegidos
```

Além da autenticação, a API utiliza perfis de acesso para restringir determinados recursos.

Os perfis definidos no projeto são:

* `CLIENTE`
* `ATENDENTE`
* `COZINHEIRO`
* `GERENTE`

Os testes T02 e T03 verificam, respectivamente, situações de ausência de autenticação e falta de permissão.

---

## 12. Logs e auditoria

**Status: não implementado.**

O projeto não possui, neste momento, um mecanismo específico de logs de auditoria para registrar ações sensíveis da aplicação.

Essa funcionalidade não foi incluída no escopo do MVP entregue, que priorizou a implementação e validação do fluxo principal obrigatório:

```text
Pedido → Pagamento mock → Atualização de status
```

A ausência de logs/auditoria deve ser considerada uma limitação da versão atual do projeto.

---

## 13. Evidências dos testes

As evidências de execução dos testes podem ser reproduzidas através da coleção Postman disponibilizada em:

```text
testes/Postman/Raizes-do-Nordeste.postman_collection.json
```

As requisições estão organizadas nas seguintes pastas:

```text
Auth
Produtos
Pedidos
Pagamento
Erros
```

A coleção contém os cenários definidos neste documento e permite reproduzir o fluxo principal e os cenários de validação da API.

---

## 14. Considerações finais

Os testes realizados demonstraram o funcionamento do fluxo principal do MVP com persistência em banco de dados, incluindo:

* autenticação por JWT;
* controle de acesso por perfil;
* criação de pedidos;
* validação de produtos, unidades e estoque;
* persistência dos pedidos;
* processamento de pagamento mock;
* aprovação e recusa de pagamentos;
* atualização do status do pedido;
* tratamento padronizado das principais situações de erro.

A coleção Postman e este documento foram disponibilizados no repositório do projeto para permitir a reprodução dos testes durante a avaliação.


