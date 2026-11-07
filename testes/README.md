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

---

## 5. Dados utilizados nos testes

Os testes foram realizados utilizando dados previamente cadastrados no banco de dados.

### Produtos

| ID | Nome | Preço | Validade |
|---|---|---|---|
| 1 | Coxinha de Frango | R$ 8,50 | 31/12/2026 |
| 2 | Pastel de Carne | R$ 9,00 | 31/12/2026 |

### Unidades

| ID | Nome | Endereço |
|---|---|---|
| 1 | Unidade Centro | Rua Principal, 100 |
| 2 | Raizes do Nordeste - Unidade 2 | Av. Paulista, 1000 - São Paulo |


### Usuários de teste

Os usuários abaixo são utilizados nos testes de autenticação e autorização da API.

| ID | Nome | E-mail | Senha | Perfil |
|---|---|---|---|---|
| 1 | Usuario Teste | `teste@teste.com` | `123456` | CLIENTE |
| 2 | Cliente Teste 2 | `cliente2@exemplo.com` | `123456` | CLIENTE |
| 3 | Gerente Teste | `gerente.teste@exemplo.com` | `GerenteTeste@2026` | GERENTE |

Os usuários são fictícios e destinados exclusivamente ao ambiente local de desenvolvimento e testes.

As senhas são armazenadas no banco de dados utilizando hashes BCrypt, não sendo persistidas em texto puro.

Os registros necessários estão definidos no arquivo `database/dados_iniciais.sql`, que deve ser executado após a criação das tabelas pelo Hibernate.

**Utilização nos testes:**

- **T01:** autenticação do usuário CLIENTE (ID 1).
- **T15:** consulta dos próprios dados pelo CLIENTE.
- **T16:** tentativa de acesso do CLIENTE aos dados de outro usuário (ID 2).
- **T17:** consulta dos dados do usuário de ID 2 pelo GERENTE.
- **T18:** autenticação do usuário GERENTE (ID 3).

As credenciais apresentadas são exclusivas para demonstração e não devem ser utilizadas em ambientes de produção.

### Estoque

O registro de estoque utilizado nos testes corresponde ao produto `1` na unidade `1`. A quantidade disponível pode variar conforme a execução dos testes de pagamento aprovado, que consomem estoque.


| Produto | Unidade | Quantidade de referência |
|---|---|---:|
| Coxinha de Frango | Unidade Centro | 10 |
| Pastel de Carne | Unidade Centro | 0 |


Antes de executar a sequência completa, confira a quantidade atual no banco de dados. Se necessário, restaure o estoque para 10 unidades com a seguinte instrução SQL:

```sql
UPDATE estoque_produto
SET quantidade = 10
WHERE id = 1
  AND produto_id = 1
  AND unidade_id = 1;
```

O teste T08 utiliza a variável de ambiente `quantidadeEstoqueInsuficiente`, configurada com o valor `9999`, para solicitar uma quantidade superior ao estoque de referência e verificar o retorno `409 Conflict`.

---

## 6. Configuração do Postman

A coleção de testes utiliza a variável:

```text
baseUrl = http://localhost:8080
```

Também são utilizadas duas variáveis de ambiente para armazenar os tokens JWT:

```text
tokenCliente
tokenGerente
```

As variáveis `tokenCliente` e `tokenGerente` inicialmente devem estar vazias.

A separação dos tokens foi necessária após a inclusão dos novos testes de autenticação e autorização, que verificam o acesso aos endpoints conforme o perfil do usuário.

- **`tokenCliente`**: armazena o JWT de um usuário com perfil `CLIENTE`, utilizado nas requisições que exigem autenticação desse perfil.
- **`tokenGerente`**: armazena o JWT de um usuário com perfil `GERENTE`, utilizado nos testes que exigem permissões gerenciais.

Essa configuração permite executar testes com diferentes níveis de permissão sem precisar substituir manualmente um único token entre as requisições.


### Obtenção dos tokens

A coleção utiliza dois testes de autenticação para obter os tokens JWT dos perfis CLIENTE e GERENTE.

**T01 - Login válido (CLIENTE)**

O teste T01 realiza a autenticação através do endpoint:

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

Após uma autenticação bem-sucedida, o script de pós-resposta do Postman armazena automaticamente o JWT recebido na variável de ambiente `tokenCliente`.

Os testes autenticados com o perfil CLIENTE utilizam o cabeçalho:

```text
Authorization: Bearer {{tokenCliente}}
```

**T18 - Login válido (GERENTE)**

O teste T18 realiza a autenticação de um usuário com perfil GERENTE através do mesmo endpoint:

```text
POST /auth
```

com as credenciais:

```json
{
    "email": "gerente.teste@exemplo.com",
    "senha": "GerenteTeste@2026"
}
```

Após uma autenticação bem-sucedida, o script de pós-resposta do Postman armazena automaticamente o JWT recebido na variável de ambiente `tokenGerente`.

Os testes autenticados com o perfil GERENTE utilizam o cabeçalho:

```text
Authorization: Bearer {{tokenGerente}}
```

> **Importante:** a separação dos tokens permite executar os testes de autenticação e autorização com diferentes perfis, sem a necessidade de substituir manualmente o JWT entre as requisições. Os testes T01 e T18 devem ser executados antes das requisições que dependem dos respectivos tokens.


### Importação e execução da coleção

1. Abra o Postman.
2. Clique em **Import** e selecione o arquivo
   `testes/Postman/Raizes-do-Nordeste.postman_collection.json`.
3. Verifique se a aplicação Spring Boot está em execução
   em `http://localhost:8080`.
4. Confira se a variável `baseUrl` está configurada como
   `http://localhost:8080` e se as variáveis `tokenCliente`
   e `tokenGerente` estão inicialmente vazias.
5. Abra a coleção importada e selecione **Run** para acessar
   o Collection Runner.
6. Confira a ordem das requisições conforme a seção 7
   deste documento, garantindo que os tokens necessários
   sejam obtidos antes dos testes que dependem deles.
7. Execute a coleção e verifique os resultados dos testes
   apresentados pelo Postman.

**Observação:** os testes dependem dos dados iniciais
descritos na seção 5. Antes de executar novamente a coleção,
verifique o estoque disponível, pois pagamentos aprovados
consomem unidades dos produtos.

---


## 7. Ordem recomendada para execução

Para reproduzir os testes, recomenda-se executar as requisições na seguinte ordem:

1. **Autenticação**
   - T01 — Login válido (CLIENTE)
   - T18 — Login válido (GERENTE)
   - T02 — Acesso sem token
   - T03 — Perfil sem permissão
   - T14 — Consulta de usuário sem token
   - T15 — Cliente consulta próprio usuário
   - T16 — Cliente consulta outro usuário
   - T17 — Gerente consulta outro usuário

2. **Produtos**
   - T06 — Produto inexistente
   - T11 — Cardápio com produtos disponíveis
   - T12 — Cardápio de unidade sem produtos
   - T13 — Cardápio de unidade inexistente

3. **Fluxos de Pedido e Pagamento**
   - T09.1 — Criar pedido para pagamento
   - T09.1.1 — Pagamento sem campo aprovado
   - T09.2 — Pagamento aprovado
   - T09.2.1 — Pagamento recusado
   - T09.3 — Confirmar status do pedido
   - T09.4 — Alterar status para EM_PREPARACAO
   - T09.5 — Cliente sem permissão para alterar status
   - T10.1 — Criar pedido para pagamento recusado
   - T10.2 — Pagamento recusado
   - T10.3 — Confirmar status após pagamento recusado

4. **Erros**
   - T04 — Campo obrigatório ausente
   - T05 — Quantidade negativa
   - T07 — Unidade inexistente
   - T08 — Estoque insuficiente

Os testes T01 e T18 devem ser executados antes das requisições autenticadas, pois seus scripts de pós-resposta armazenam automaticamente os tokens JWT nas variáveis de ambiente `tokenCliente` e `tokenGerente`, respectivamente.

Os testes T09 e T10 possuem mais de uma requisição porque representam fluxos completos, nos quais o resultado de uma etapa é utilizado na etapa seguinte.

---

## 8. Cenários de teste

Os cenários abaixo foram executados utilizando a coleção Postman disponibilizada no diretório `testes/Postman`.

### T01 — Login válido(CLIENTE)

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

O script de pós-resposta do Postman armazena automaticamente o token JWT na variável de ambiente `tokenCliente`, permitindo sua utilização nas requisições que exigem autenticação com perfil CLIENTE.

**Evidência:** requisição `T01 - Login válido(CLIENTE)` na pasta `Auth`.

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

**Pré-condição:** usuário autenticado, produto e unidade existentes e estoque disponível inferior à quantidade solicitada no teste (`9999` unidades).

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

**Evidência:** requisição `T09.1 - Criar pedido para pagamento` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.1.1 — Validar pagamento sem o campo `aprovado`

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T09.1, com `pedidoId` válido.

**Entrada:** pagamento mock contendo `pedidoId` e `formaPagamento`, sem o campo `aprovado`.

**Resultado esperado:** `400 Bad Request`, devido à ausência do campo obrigatório `aprovado`.

**Evidência:** requisição `T09.1.1 - Pagamento sem campo aprovado` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.2 — Pagamento aprovado

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T09.1 ainda sem pagamento registrado.

**Entrada:** pagamento mock com aprovação.

**Resultado esperado:** `200 OK`, pagamento com status `APROVADO`.

**Evidência:** requisição `T09.2 - Pagamento aprovado` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.2.1 — Pagamento recusado

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T09.1 ainda sem pagamento registrado.

**Entrada:** pagamento mock com reprovação.

**Resultado esperado:** `200 OK`, pagamento com status `RECUSADO`.

**Evidência:** requisição `T09.2.1 - Pagamento recusado` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.3 — Confirmar status do pedido

**Endpoint:** `GET /pedidos/{id}`

**Pré-condição:** pagamento do pedido aprovado no T09.2.

**Entrada:** ID do pedido criado no T09.1.

**Resultado esperado:** `200 OK`, com o pedido apresentando status `PAGAMENTO_APROVADO`.

**Evidência:** requisição `T09.3 - Confirmar status do pedido` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.4 — Alterar status do pedido para EM_PREPARACAO

**Endpoint:** `PUT /pedidos/{{pedidoIdAprovacao}}/status?novoStatus=EM_PREPARACAO`

**Pré-condição:** pagamento do pedido aprovado no T09.2 e usuário deve estar autenticado como perfil GERENTE.

**Entrada:** ID do pedido criado no T09.1 e token do GERENTE.

**Resultado esperado:** `200 OK`, com o pedido apresentando status `EM_PREPARACAO`.

**Evidência:** requisição `T09.4 - Alterar status do pedido para EM_PREPARACAO` na pasta `Fluxos de Pedido e Pagamento`.

#### T09.5 — Cliente sem permissão para alterar status

**Endpoint:** `PUT /pedidos/{{pedidoIdAprovacao}}/status?novoStatus=EM_PREPARACAO`

**Pré-condição:** usuário autenticado com perfil CLIENTE e pedido existente.

**Entrada:** ID do pedido criado no T09.1 e token do CLIENTE.

**Resultado esperado:** `403 Forbidden`, com o pedido apresentando status `SEM_PERMISSAO`.

**Evidência:** requisição `T09.5 - Cliente sem permissão para alterar status` na pasta `Fluxos de Pedido e Pagamento`.

---

### T10 — Pagamento recusado

O cenário T10 representa o comportamento da API quando o pagamento mock é recusado.

#### T10.1 — Criar pedido para pagamento recusado

**Endpoint:** `POST /pedidos`

**Pré-condição:** usuário autenticado, produto e unidade existentes e estoque disponível.

**Entrada:** pedido válido.

**Resultado esperado:** `201 Created`, com status inicial `AGUARDANDO_PAGAMENTO`.

**Evidência:** requisição `T10.1 - Criar pedido para pagamento recusado` na pasta `Fluxos de Pedido e Pagamento`.

#### T10.2 — Pagamento recusado

**Endpoint:** `POST /pagamentos`

**Pré-condição:** pedido criado no T10.1.

**Entrada:** pagamento mock com aprovação definida como `false`.

**Resultado esperado:** `200 OK`, pagamento com status `RECUSADO`.

**Evidência:** requisição `T10.2 - Pagamento recusado` na pasta `Fluxos de Pedido e Pagamento`.

#### T10.3 — Confirmar status após pagamento recusado

**Endpoint:** `GET /pedidos/{id}`

**Pré-condição:** pagamento recusado no T10.2.

**Entrada:** ID do pedido criado no T10.1.

**Resultado esperado:** `200 OK`, mantendo o pedido no status `AGUARDANDO_PAGAMENTO`.

**Evidência:** requisição `T10.3 - Confirmar status após pagamento recusado` na pasta `Fluxos de Pedido e Pagamento`.

---

### T11 — Cardápio com produtos disponíveis

**Endpoint:** `GET /unidades/1/cardapio`

**Pré-condição:** unidade 1 cadastrada, com a Coxinha de Frango disponível em estoque e o Pastel de Carne com quantidade zero.

**Entrada:** ID da unidade (`1`).

**Resultado esperado:** `200 OK`, retornando uma lista de produtos disponíveis para a unidade.

O retorno deve conter pelo menos um produto e não deve incluir o Pastel de Carne (`produtoId = 2`), pois seu estoque está zerado.

**Evidência:** requisição `T11 - Cardápio com produtos disponíveis` na pasta `Produtos` do Postman.

---

### T12 — Cardápio de unidade sem produtos

**Endpoint:** `GET /unidades/2/cardapio`

**Pré-condição:** unidade 2 cadastrada no banco de dados, sem produtos disponíveis em seu estoque.

**Entrada:** ID da unidade (`2`).

**Resultado esperado:** `200 OK`, retornando uma lista vazia (`[]`).

Esse cenário verifica que uma unidade existente, mas sem produtos disponíveis, deve retornar um cardápio vazio, sem gerar erro.

**Evidência:** requisição `T12 - Cardápio de unidade sem produtos` na pasta `Produtos` do Postman.

---

### T13 — Cardápio de unidade inexistente

**Endpoint:** `GET /unidades/9999/cardapio`

**Pré-condição:** não enviar o cabeçalho `Authorization`

**Entrada:** ID de uma unidade inexistente (`9999`).

**Resultado esperado:** `404 Not Found`, com a mensagem:

```text
Unidade não encontrada
```

Esse cenário verifica se a API identifica corretamente uma unidade inexistente e retorna uma resposta de erro apropriada.

**Evidência:** requisição `T13 - Cardápio de unidade inexistente` na pasta `Produtos` do Postman.

---

### T14 — Consulta de usuário sem token

**Endpoint:** `GET /usuarios/2`

**Pré-condição:** não enviar o cabeçalho `Authorization`.

**Entrada:** ID de uma usuario existente (`2`).

**Resultado esperado:** `401 Unauthorized`, com a mensagem:

```text
É necessário autenticar para acessar este recurso.
```

Esse cenário verifica se a API impede o acesso aos dados de um usuário quando a requisição não possui um token JWT de autentificação, retornando a resposta 401 Unauthorized.

**Evidência:** requisição `T14 - Consulta de usuário sem token` na pasta `Auth` do Postman.

---

### T15 — Cliente consulta próprio usuário

**Endpoint:** `GET /usuarios/1`

**Pré-condição:** CLIENTE ID 1 autenticado com token JWT válido (`{{tokenCliente}}`).

**Entrada:** ID de um usuário existente (`1`).

**Resultado esperado:** `200 OK`, com retorno dos dados do próprio usuário autenticado.

Esse cenário verifica se a API permite que um cliente autenticado consulte seus próprios dados, garantindo o acesso autorizado às suas informações.

**Evidência:** requisição `T15 - Cliente consulta próprio usuário` na pasta `Auth` do Postman.

---

### T16 — Cliente consulta outro usuário

**Endpoint:** `GET /usuarios/2`

**Pré-condição:** CLIENTE ID 1 autenticado com token JWT válido (`{{tokenCliente}}`).

**Entrada:** ID de um usuario existente (`2`).

**Resultado esperado:** `403 Forbidden`, com a mensagem:

```text
Você não tem permissão para acessar este recurso.
```

Esse cenário verifica se a API impedeque um cliente autenticado consulte os dados de outro usuario, retornando 403 Forbidden.

**Evidência:** requisição `T16 - Cliente consulta outro usuário` na pasta `Auth` do Postman.

---

### T17 — Gerente consulta outro usuário

**Endpoint:** `GET /usuarios/2`

**Pré-condição:** GERENTE autenticado com token JWT válido (`{{tokenGerente}}`).

**Entrada:** ID de um usuário existente (`2`).

**Resultado esperado:** `200 OK`, com retorno dos dados do usuário consultado.

Esse cenário verifica se a API permite que um usuário com perfil GERENTE consulte os dados de outro usuário.

**Evidência:** requisição `T17 - Gerente consulta outro usuário` na pasta `Auth` do Postman.

---

### T18 — Login válido(GERENTE)

**Endpoint:** `POST /auth`

**Pré-condição:** usuário  GERENTE previamente cadastrado.

**Entrada:**

```json
{
  "email": "gerente.teste@exemplo.com",
  "senha": "GerenteTeste@2026"
}
```

**Resultado esperado:** `200 OK`, com retorno dos dados do usuário e um token JWT válido.

O script de pós-resposta do Postman armazena automaticamente o token JWT na variável de ambiente `tokenGerente`, permitindo sua utilização nas requisições que exigem autenticação com perfil GERENTE.

**Evidência:** requisição `T18 - Login válido(GERENTE)` na pasta `Auth` do Postman.

----

## 9. Resumo dos cenários

| ID | Tipo | Método | Endpoint | Resultado esperado |
|---|---|---|---|---|
| T01 | Positivo | POST | `/auth` | 200 |
| T02 | Negativo | GET | `/pedidos/{id}` | 401 |
| T03 | Negativo | POST | `/fidelidade/{id}/pontos` | 403 |
| T04 | Negativo | POST | `/pedidos` | 400 |
| T05 | Negativo | POST | `/pedidos` | 400 |
| T06 | Negativo | POST | `/pedidos` | 404 |
| T07 | Negativo | POST | `/pedidos` | 404 |
| T08 | Negativo | POST | `/pedidos` | 409 |
| T09.1 | Positivo | POST | `/pedidos` | 201 |
| T09.1.1 | Negativo | POST | `/pagamentos` | 400 |
| T09.2 | Positivo | POST | `/pagamentos` | 200 |
| T09.2.1 | Positivo | POST | `/pagamentos` | 200 |
| T09.3 | Positivo | GET | `/pedidos/{id}` | 200 |
| T09.4 | Positivo | PUT | `/pedidos/{id}/status` | 200 |
| T09.5 | Negativo | PUT | `/pedidos/{id}/status` | 403 |
| T10.1 | Positivo | POST | `/pedidos` | 201 |
| T10.2 | Positivo | POST | `/pagamentos` | 200 |
| T10.3 | Positivo | GET | `/pedidos/{id}` | 200 |
| T11 | Positivo | GET | `/unidades/1/cardapio` | 200 |
| T12 | Positivo | GET | `/unidades/2/cardapio` | 200 |
| T13 | Negativo | GET | `/unidades/9999/cardapio` | 404 |
| T14 | Negativo | GET | `/usuarios/2` | 401 |
| T15 | Positivo | GET | `/usuarios/1` | 200 |
| T16 | Negativo | GET | `/usuarios/2` | 403 |
| T17 | Positivo | GET | `/usuarios/2` | 200 |
| T18 | Positivo | POST | `/auth` | 200 |
---

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
Armazenamento do token no ambiente do Postman
  ↓
Authorization: Bearer {{tokenCliente}}
ou
Authorization: Bearer {{tokenGerente}}
  ↓
Acesso aos recursos protegidos
```

Além da autenticação, a API utiliza perfis de acesso para restringir determinados recursos.

Os perfis definidos no projeto são:

- `CLIENTE`
- `ATENDENTE`
- `COZINHEIRO`
- `GERENTE`

Os testes de autenticação e autorização verificam os seguintes cenários:

- **T01:** login válido com perfil CLIENTE.
- **T02:** tentativa de acesso sem autenticação.
- **T03:** tentativa de acesso sem a permissão necessária.
- **T14:** consulta de usuário sem token JWT.
- **T15:** cliente autenticado consultando seus próprios dados.
- **T16:** cliente autenticado tentando consultar dados de outro usuário.
- **T17:** gerente autenticado consultando dados de outro usuário.
- **T18:** login válido com perfil GERENTE.

Esses testes verificam se a API identifica corretamente os usuários autenticados e aplica as permissões de acesso conforme seus respectivos perfis.

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
Fluxos de Pedido e Pagamento
Erros
```

A coleção contém os cenários definidos neste documento e permite reproduzir o fluxo principal e os cenários de validação da API.

Para executar os testes que exigem autenticação, é necessário configurar o ambiente do Postman conforme descrito na seção 6, utilizando as variáveis `baseUrl`, `tokenCliente` e `tokenGerente`.

Os testes T01 e T18 realizam o login dos respectivos usuários e armazenam automaticamente os tokens JWT nas variáveis de ambiente correspondentes.

---


## 14. Considerações finais

Os testes realizados demonstraram o funcionamento do fluxo principal do MVP com persistência em banco de dados, incluindo:

- autenticação por JWT;
- controle de acesso por perfil;
- criação de pedidos;
- validação de produtos, unidades e estoque;
- persistência dos pedidos;
- processamento de pagamento mock;
- aprovação e recusa de pagamentos;
- atualização do status do pedido;
- tratamento padronizado das principais situações de erro.

A coleção Postman e este documento foram disponibilizados no repositório do projeto para permitir a reprodução dos testes durante a avaliação.

