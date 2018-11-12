# Bates - Projeto que faz o cadastro e checkIn de clientes de um hotel!

### Requisitos funcionais
* Um CRUDL para o cadastro de hóspedes;
* No check in deve ser possível buscar hóspedes cadastrados pelo nome,
documento ou telefone;
* Consultar hóspedes que já realizaram o check in e não estão mais no hotel;
* Consultar hóspedes que ainda estão no hotel;
* As consultas devem apresentar o valor (Valor total e o valor da última
hospedagem) já gasto pelo hóspede no hotel;

### Regras de negócio
* Uma diária no hotel de segunda à sexta custa R$120,00;
* Uma diária no hotel em finais de semana custa R$150,00;
* Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo
diário, sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
* Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra;


## Técnologias utilizadas

### Backend
* [Java](https://java.com/en/download/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [H2 batabase](http://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Maven](https://maven.apache.org/)

## Executando a aplicação

1. Como pré-requisito, possuir [docker](https://www.docker.com/).
2. Baixar o arquivo docker-compose.yml deste repositório e executar o comando: docker-compose up.

> Será baixada a imagem do banco de dados e da aplicação, onde a mesma ficará disponível na porta 8080.
> Importante: A imagem do banco de dados que o docker irá iniciar utiliza a porta 5432, fica imprescindível ter esta porta disponível ao iniciar a orquestragem dos containers.

### API's disponíveis
* [Wiki do projeto](https://github.com/aliniribeiroo/bates/wiki/API's)
