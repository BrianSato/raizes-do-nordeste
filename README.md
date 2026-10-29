# Raízes do Nordeste — API REST

API REST desenvolvida como parte do projeto multidisciplinar do curso de Análise e Desenvolvimento de Sistemas da UNINTER.

O projeto tem como objetivo desenvolver o backend de um sistema de pedidos de alimentos para uma rede de unidades do Raízes do Nordeste, contemplando o gerenciamento de pedidos, o processamento simulado de pagamentos, o controle de estoque e a fidelização de clientes.

## Objetivo do projeto

Implementar uma API REST organizada em camadas, com regras de negócio, persistência de dados em banco relacional, autenticação e autorização de usuários, além de testes para validar os principais fluxos da aplicação.

## Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — desenvolvimento da API REST.
- **Spring Data JPA / Hibernate** — persistência e mapeamento objeto-relacional.
- **MySQL** — banco de dados relacional.
- **Maven** — gerenciamento de dependências e build.
- **JWT** — autenticação baseada em tokens.
- **Postman** — execução e validação de requisições HTTP.
- **Git e GitHub** — versionamento e hospedagem do código-fonte.
- **Eclipse IDE** — ambiente de desenvolvimento.

## Funcionalidades e regras de negócio

O projeto contempla o desenvolvimento das seguintes áreas:

- **Autenticação e autorização:** acesso protegido por token JWT e controle de permissões conforme o perfil do usuário.
- **Pedidos:** criação de pedidos com validação dos dados recebidos e dos produtos e unidades envolvidos.
- **Pagamentos:** simulação de aprovação ou recusa de pagamentos, sem integração com um gateway financeiro real.
- **Estoque:** validação da disponibilidade de produtos e aplicação das regras de movimentação relacionadas aos pedidos.
- **Fidelização:** gerenciamento de pontos, considerando o consentimento do cliente e as regras de utilização do saldo.
- **Validação e tratamento de erros:** respostas HTTP adequadas para situações como dados inválidos, falta de autorização, recursos inexistentes e conflitos de negócio.

### Fluxo principal

O fluxo principal previsto para o MVP é:

1. Criação do pedido.
2. Processamento simulado do pagamento.
3. Atualização do status do pedido conforme o resultado do pagamento.
4. Aplicação das regras de negócio relacionadas ao estoque e à fidelização, quando aplicáveis.

O pagamento é simulado para fins acadêmicos e de validação das regras de negócio.

## Estrutura do projeto

O projeto segue uma organização em camadas, separando as responsabilidades de comunicação com a API, aplicação, domínio e infraestrutura.

```text
src/main/java/raizes_do_nordeste/
├── api/
│   ├── controller/
│   ├── dto/
│   └── exception/
├── application/
│   └── service/
├── config/
├── domain/
│   ├── entity/
│   └── enums/
└── infrastructure/
    └── repository/
```

### Responsabilidades dos pacotes

- **`api.controller`** — recebe as requisições HTTP e disponibiliza os endpoints da API.
- **`api.dto`** — define os objetos utilizados na entrada e na saída de dados da API.
- **`api.exception`** — concentra o tratamento de exceções e erros da API.
- **`application.service`** — coordena as operações e os casos de uso da aplicação.
- **`config`** — reúne as configurações necessárias para o funcionamento da aplicação.
- **`domain.entity`** — contém as entidades que representam os conceitos e dados do negócio.
- **`domain.enums`** — define enumerações utilizadas nas regras e nos estados do domínio.
- **`infrastructure.repository`** — reúne os componentes responsáveis pelo acesso e pela persistência dos dados.

Essa organização busca separar as responsabilidades, facilitar a manutenção e tornar a evolução da aplicação mais clara.

## Como executar o projeto

### Pré-requisitos

- JDK compatível com a versão configurada no projeto.
- Maven.
- MySQL.
- Uma ferramenta para realizar requisições HTTP, como o Postman.

### Configuração do banco de dados

1. Instale e configure o MySQL.
2. Crie um banco de dados e um usuário MySQL com as permissões necessárias.
3. Configure as seguintes variáveis de ambiente:

| Variável | Descrição |
|---|---|
| `DB_URL` | URL de conexão com o banco MySQL criado pelo usuário. Exemplo: `jdbc:mysql://localhost:3306/meu_banco` |
| `DB_USERNAME` | Nome do usuário MySQL configurado para acessar o banco. |
| `DB_PASSWORD` | Senha definida pelo próprio usuário ao configurar o MySQL. |
| `JWT_SECRET` | Chave secreta utilizada para assinar e validar os tokens JWT. |

As variáveis podem ser configuradas no sistema operacional ou nas configurações de execução da IDE, como a aba **Environment** do Eclipse.

O arquivo `.env.example`, disponível na raiz do projeto, apresenta um
modelo das variáveis de ambiente necessárias. Os valores são ilustrativos
e devem ser substituídos pelas configurações do ambiente local.

O Spring Boot não carrega automaticamente esse arquivo. As variáveis
devem ser configuradas no sistema operacional ou na IDE.

O arquivo `application.properties` utiliza essas variáveis para obter as configurações necessárias, sem armazenar credenciais no código-fonte.

**Importante:** não publique senhas, chaves secretas ou tokens reais no repositório.

### Dados iniciais para testes

O projeto possui um script SQL com os dados necessários para executar os testes da API.

Arquivo: `database/dados_iniciais.sql`

Para utilizar:

1. Crie o banco de dados `raizes_nordeste` no MySQL.
2. Configure as variáveis de ambiente de conexão com o banco.
3. Execute a aplicação Spring Boot para que o Hibernate crie as tabelas.
4. Abra o arquivo `database/dados_iniciais.sql` no MySQL Workbench.
5. Execute o script para cadastrar os dados iniciais.

O script inclui:
- Um usuário de teste com perfil CLIENTE.
- Uma unidade.
- Um produto.
- O estoque inicial do produto.

**Credenciais de teste:**

- E-mail: `teste@teste.com`
- Senha: `123456`

Essas credenciais são fictícias e destinadas exclusivamente ao ambiente de desenvolvimento e testes.

### Executando a aplicação

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

Também é possível importar o projeto Maven no Eclipse e executá-lo pela IDE.

A inicialização depende de o banco de dados estar disponível e das configurações locais estarem corretas.

### Documentação da API — Swagger

Com a aplicação em execução, a documentação interativa dos endpoints está disponível em:

http://localhost:8080/swagger-ui/index.html

O Swagger permite consultar os endpoints, os parâmetros e os modelos de requisição e resposta da API.

## Testes da API

Os principais cenários de teste são organizados no Postman, incluindo autenticação, autorização, validações de entrada, criação de pedidos e resultados de pagamentos simulados.

- [Plano de testes e instruções de execução](testes/README.md)
- [Coleção Postman](testes/Postman/Raizes-do-Nordeste.postman_collection.json)

Consulte o plano de testes para verificar as pré-condições, a ordem de execução, os resultados esperados e os dados necessários para reproduzir os cenários.

## Escopo atual

O projeto está sendo desenvolvido de forma incremental, priorizando a implementação e a validação do fluxo principal do MVP. Nem todas as funcionalidades previstas necessariamente estão concluídas.

O processamento de pagamentos é simulado, e o projeto não representa um sistema financeiro real. A interface gráfica não faz parte do escopo deste repositório, que é dedicado ao backend.

## Diagramas do projeto

Os diagramas utilizados na modelagem do sistema estão disponíveis abaixo:

- [DER — Diagrama Entidade-Relacionamento](documentacao/diagramas/projeto_multidiciplinar_DER.drawio.png)
- [DCD — Diagrama de Classes](documentacao/diagramas/projeto_multidiciplinar_UML.drawio.png)
- [DCU — Diagrama de Casos de Uso](documentacao/diagramas/projeto_multiciplinar_DCU.drawio.png)

## Autor

**Brian Sato**

Estudante de Análise e Desenvolvimento de Sistemas — UNINTER.

GitHub: [BrianSato](https://github.com/BrianSato)