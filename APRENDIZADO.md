# Aprendizado do Projeto — Revenda de Carros API

Este arquivo documenta o raciocínio por trás das decisões técnicas do projeto,
não só o "como", mas o "porquê" de cada peça. Serve como registro de estudo
e como referência rápida para relembrar decisões tomadas.

## Arquitetura em camadas

O projeto segue separação de responsabilidades em pacotes:

model → entidades (representam tabelas do banco)
repository → acesso a dados (interfaces, Spring gera a implementação)
service → regras de negócio (validações, orquestração)
controller → endpoints HTTP (recebe requisição, delega pro service)
dto → objetos de entrada/saída da API (não expõe a entidade direto)
config → configurações gerais (ex: PasswordEncoder)
security → lógica específica de autenticação (JwtService)


Fluxo de uma requisição: `Controller → Service → Repository → Banco`.
O Controller nunca fala direto com o Repository — isso evitaria centralizar
a regra de negócio, e ela ficaria espalhada/duplicada em vários lugares.

Em Java, pacote e diretório são a mesma coisa: a pasta no disco precisa
bater exatamente com a declaração `package` no topo do arquivo.

## Entidades (@Entity)

`@Entity` diz ao Hibernate que a classe representa uma tabela real e
persistente, não só um objeto que existe enquanto o programa roda.
`@Table(name = "...")` conecta explicitamente com o nome da tabela —
nunca depender de coincidência entre nome de classe e nome de tabela.

Cada `@Column` espelha uma regra que já existe no SQL da migration
(`nullable`, `unique`, `length`). O Hibernate usa isso pra **validar**
(configuração `ddl-auto=validate`), nunca pra alterar o banco sozinho.

## Enum Role

Um `enum` é uma lista fechada de valores válidos. Não se usa `new` com
enum — os valores já existem, você só aponta pra um deles (`Role.ADMIN`).

`@Enumerated(EnumType.STRING)` salva o **nome** do valor no banco
(`"ADMIN"`), não a posição (`ORDINAL`). Se salvasse por posição e o enum
fosse reordenado, usuários trocariam de cargo silenciosamente — um risco
de segurança real, não só um detalhe estético.

## Optional

Método que busca algo no banco pode não achar nada. Sem `Optional`, isso
vira `null`, e usar um `null` como objeto real causa `NullPointerException`
— erro que só aparece em tempo de execução, não na compilação.
`Optional` é uma "caixa" que obriga a checar explicitamente antes de usar.

## Interface vs Class

- **Repository é `interface`**: o Spring gera a implementação sozinho a
  partir do nome do método (`findByEmail`). Basta herdar de
  `JpaRepository<Entidade, TipoDoId>`.
- **Service é `class`**: regra de negócio só existe se alguém escrever —
  nada disso é gerado automaticamente.

## Injeção de dependência

O Spring nunca é instanciado manualmente com `new`. Classes marcadas com
`@Service`, `@RestController`, etc. têm suas dependências entregues
automaticamente pelo construtor — o Spring olha o que o construtor pede
e entrega pronto, na inicialização da aplicação.

Campos injetados são `private final`: nunca trocam depois de criados.

## @Configuration e @Bean

Usados quando uma dependência não tem implementação óbvia. `PasswordEncoder`
é uma interface genérica — existem várias implementações possíveis
(BCrypt é uma delas). O `@Bean` é a "receita" que diz explicitamente
qual implementação usar.

## DTO

Um DTO (`CriarUsuarioRequest`, `LoginRequest`) é um envelope específico
pro que entra/sai numa requisição, sem misturar com a entidade real
(que carrega campos internos como `id` e `criadoEm`).

## BCrypt vs JWT

- **BCrypt**: transforma a senha num hash irreversível, guardado no banco.
  Nunca descriptografa — login funciona comparando hashes, nunca revertendo.
- **JWT**: token gerado **depois** que a senha já foi confirmada, prova
  "já estou logado" nas próximas requisições sem repetir email/senha.

Chave secreta e tempo de expiração do JWT ficam no `application.properties`
(via `@Value`), nunca hardcoded — permite trocar por ambiente sem recompilar,
e evita expor a chave no histórico do Git.

## Spring Security — comportamento padrão

Assim que a dependência é adicionada, o Security **bloqueia tudo** por
padrão (por isso os 401 nos primeiros testes de `/usuarios` e `/clientes`).
`SecurityFilterChain` define regras explícitas: rotas públicas
(`permitAll()`) e rotas que exigem autenticação (`authenticated()`).

`csrf.disable()` é necessário em APIs REST puras (sem formulário HTML
tradicional). A forma correta e atual é `.csrf(AbstractHttpConfigurer::disable)`.

## Docker + Flyway

Docker isola o Postgres do sistema operacional, evitando conflitos com
instalações nativas (aprendido na prática: conflito de porta 5432 com
um Postgres instalado por curso anterior).

Flyway versiona o schema via arquivos SQL numerados (`V1__...`, `V2__...`),
cada um rodando uma única vez, na ordem. Alternativa ao `ddl-auto=update`,
que deixaria o Hibernate alterar o banco sozinho, de forma imprevisível.

---

## Roadmap — o que foi construído, em ordem

### Setup do ambiente
1. Instalar Git, JDK 21, IntelliJ, Docker Desktop (WSL2), Node + Angular CLI
2. Gerar projeto Spring Boot (start.spring.io): Web, JPA, PostgreSQL Driver,
   Security, Validation, Flyway, Lombok, DevTools
3. Criar `docker-compose.yml` — serviço Postgres com nome do banco,
   usuário, senha, porta, volume persistente
4. Subir o banco: `docker-compose up -d`
5. Configurar `application.properties` — conexão, `ddl-auto=validate`,
   caminho das migrations
6. Criar `V1__create_usuario_table.sql` — tabela usuario
7. Rodar a aplicação — Flyway aplica a migration

### Entidade Usuario
8. Criar pacotes: model, repository, service, controller, dto
9. `Role` (enum) — ADMIN, GERENTE, VENDEDOR
10. `Usuario` (entity) — @Entity, @Table, @Id/@GeneratedValue, @Column,
    @Enumerated(STRING), @PrePersist
11. `UsuarioRepository` (interface) — extends JpaRepository, findByEmail
12. `UsuarioService` (class) — injeta repository + PasswordEncoder,
    método criar() valida duplicidade e criptografa senha
13. `SecurityConfig` — @Bean PasswordEncoder (BCryptPasswordEncoder)
14. `CriarUsuarioRequest` (DTO record)
15. `UsuarioController` — POST /usuarios

### Entidade Cliente
16. `V2__create_cliente_table.sql` — nome, cpf (unique), telefone,
    email (unique), cidade, criado_em
17. `Cliente` (entity) — mesma estrutura de Usuario
18. `ClienteRepository` — findByEmail, findByCpf
19. `ClienteService` — sem PasswordEncoder (cliente não loga),
    valida duplicidade de cpf e email
20. `CriarClienteRequest` (DTO) + `ClienteController` — POST /clientes

### Autenticação JWT
21. Dependências JJWT no pom.xml (jjwt-api, jjwt-impl, jjwt-jackson)
22. `jwt.secret` e `jwt.expiration` no application.properties
23. `JwtService` — gerarToken, tokenValido, extrairEmail
24. `LoginRequest` e `LoginResponse` (DTOs)
25. `AuthController` — POST /auth/login
26. `SecurityConfig` atualizado — SecurityFilterChain, csrf disable,
    /auth/** e /usuarios liberados temporariamente, resto autenticado

### Próximo passo
27. `JwtAuthenticationFilter` — o filtro que lê o token do cabeçalho
    Authorization e valida a autenticação de fato
28. ---

## Fase 1 concluída — Veiculo

### Campo definido internamente vs vindo de fora

Nem todo campo de uma entidade precisa vir do `request` do cliente. Quando
um veículo é cadastrado, `status` nunca é perguntado — o `Service` sempre
define `StatusVeiculo.DISPONIVEL` por conta própria, porque um veículo
recém-cadastrado sempre nasce disponível. Isso é uma regra de negócio,
não uma limitação técnica: o `DTO` de entrada simplesmente não tem esse
campo, então não existe nem a possibilidade de alguém "escolher" um
status inicial diferente.

### BigDecimal para dinheiro

`float`/`double` têm imprecisão de arredondamento (ex: 0.1 + 0.2 não dá
exatamente 0.3). Para valores monetários, o tipo correto em Java é
`BigDecimal`, que representa decimais com precisão exata. No banco,
o tipo correspondente é `NUMERIC(precisão, escala)` — ex: `NUMERIC(10,2)`
guarda até 10 dígitos, sendo 2 depois da vírgula.

### Quando devolver a entidade direto (sem Response DTO)

`Usuario` e `Cliente` usam DTOs de resposta (`UsuarioResponse`,
`ClienteResponse`) para esconder dados sensíveis. `Veiculo` não precisa
disso — não existe campo sensível na entidade (é literalmente o produto
que a revenda quer mostrar). Regra prática: só cria um DTO de saída
quando existe algo específico para esconder ou reformatar; senão, é
complexidade desnecessária.

### Erro de migration silencioso — nome de arquivo errado

O Flyway exige o padrão exato `V{numero}__{descricao}.sql` (dois
underscores). Um nome fora do padrão não gera erro imediato — o Flyway
simplesmente **ignora** o arquivo e segue em frente, avisando só numa
linha discreta do log ("SQL migrations detected but not run"). O erro
real só aparece depois, na validação do Hibernate, como "tabela não
existe" — mesmo a migration existindo no projeto. Sempre conferir o
nome do arquivo, caractere por caractere, quando uma tabela nova
"não existe" mesmo com o SQL certo.