# Teste de Qualificação Backend O2B

## Tecnologias
* JAVA 11
* Spring Boot 2.4.x
* Spring Data JPA 2.4.x
* H2
* Lombok 1.18.x
* Maven 3.6.x

## Building and running

* `mvnw clean package`
* `java -jar ./target/o2b-project-0.0.1-SNAPSHOT.jar`

## Rodando apenas os testes

* `mvnw test`

## Ordem de execução

Usando o Postman (O2B.postman_collection.json)

* Crie um novo usuário
* Autentique o usuário recém criado
* Use o token gerado como Authorization Header para poder chamar as APIs

## Explicação e suposições do projeto

* Criado com Spring Boot pela sua baixa necessidade de configuração.
* Criado com camadas para facilitar a manutenção.
* Camada de serviço foi utilizada para facilitar a reutilização.
* Fiz usando REST, mas poderia ser feito usando GRPC.
* Usado H2, mas poderia ser facilmente alterado para PostgreSQL, MYSQL, etc.
* Usei testes de integração para testar facilmente as funcionalidades e se novas implementações não vão quebrar o que já estava funcionando (teste de regressão).
* Não usei o CEP como chave porque cidades menores (como a minha) têm apenas um CEP para toda a cidade.
* Usei DTO para que, caso a estrutura do banco de dados seja alterada, a API não necessita de alterações.