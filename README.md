🎬 Controle de Aluguel de Filmes

Sistema para controle de usuários, autenticação e gerenciamento de filmes (aluguel e devolução).
Backend desenvolvido em Java (Spring Boot) e frontend em React com TypeScript (Vite).

🚀 Funcionalidades
Backend (Java - Spring Boot)

Controle de Usuário e Autenticação

Cadastro de usuários com os campos: id, nome, email (login) e senha.

Senhas armazenadas de forma criptografada.

Login com retorno de Token JWT.

Todas as rotas protegidas, exigindo token de acesso.

Boas Práticas

Uso de DTOs para Request e Response (não expor entidades diretamente).

Lógica de negócio centralizada em camada de Service.

Tratamento básico de exceções.

Regra de Negócio

Entidade principal: Filme (id, título, gênero, status [DISPONIVEL, ALUGADO]).

Funções:

Alugar filme (apenas se estiver DISPONIVEL).

Devolver filme (alterar status para DISPONIVEL).

Frontend (React + TypeScript)

Telas

Login: Consome o endpoint de autenticação do backend.

Principal: Lista todos os filmes com botões para:

Criar novo filme.

Executar ações de alugar e devolver.

Autenticação

Token JWT salvo no Redux.

Todas as requisições enviam o token no cabeçalho Authorization.

Tela principal acessível apenas para usuários logados.

🛠️ Tecnologias Utilizadas
Backend

Java + Spring Boot

Spring Security + JWT

JPA / Hibernate

PostgreSQL

Frontend

React + TypeScript

Vite

Redux
