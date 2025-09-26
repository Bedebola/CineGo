🎬 Controle de Aluguel de Filmes

Sistema para controle de usuários, autenticação e gerenciamento de filmes (aluguel e devolução).
Backend desenvolvido em Java (Spring Boot) e frontend em React com TypeScript (Vite).

🚀 Funcionalidades:
Backend (Java - Spring Boot)
- Controle de Usuário e Autenticação
- Cadastro de usuários com os campos: id, nome, cpf, email (login) e senha.
- Login com retorno de Token JWT.
- Todas as rotas protegidas, exigindo token de acesso.

Boas Práticas:
- Uso de DTOs para Request e Response (não expor entidades diretamente).
- Lógica de negócio centralizada em camada de Service.
- Tratamento básico de exceções.

Regra de Negócio:
- Entidade principal: Filme (id, título, sinopse, gênero, status [DISPONIVEL, ALUGADO]).

Funções:
- Alugar filme (apenas se estiver DISPONIVEL).
- Devolver filme (alterar status para DISPONIVEL).

Frontend (React + TypeScript)

Telas:
- Login: Consome o endpoint de autenticação do backend.
- Principal: Dashboard com os filmes mais recentes e indicadores de quantidade de filmes em cada status.
- Catalogo de Filmes: Lista todos os filmes registrados e permite exclusão, edição e mudança de status(alugar, devolver e desativar)
- Tabela de Usuarios: Lista todos os usuarios registrados e permite exclusão e edição.
- Cadastro de Filme: Permite cadastrar um novo filme.
- Cadastro de Usuario: Permite cadastrar um novo usuario.

🛠️ Tecnologias Utilizadas
Backend:
- Java + Spring Boot
- Spring Security + JWT
- JPA / Hibernate
- MySQL

Frontend:
- React + TypeScript
- Vite
