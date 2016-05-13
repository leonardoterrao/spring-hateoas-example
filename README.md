
#### Benefício
Temos a capacidade de incluir links com informações de estado:
- Ligações dependedo de estado e do fluxo de trabalho;
- Ligações dependendo de alguma regra de negócio.

Idealmente toda lógica deve residir no servidor, mas muitas vezes você tem que mover partes para o cliente. Imagine o cenário em uma plataforma de e-commerce:
- As encomendas podem ser canceladas apenas quando estão "pendente" ou "paga", mas não quando estão "expedidas".

Como pode o aplicativo do cliente decidir quando mostrar o botão "Cancelar"? Temos que incorporar uma regra de negócio do tipo:
```javascript
    if ( order.status == PENDING || order.status == PAID ) {
     showCancelButton
    }
```
O que acontece se o processo mudar? Um novo status permite cancelar o pedido, quando estiver "em preparação". O antigo cliente não é mais compatível.
A hypermedia API pode incluir um link para a ação cancelar. O server decide quando adicionar o link. O cliente tem que saber o significado do "cancelar" (deve ser documentado) e exibir o botão quando o link estiver lá. Sem lógica de negócios muda, se a lógica muda, o cliente não requer nenhuma mudança.

Para executar o exemplo do projeto basta clocar o repositório e rodar os comandos:

```sh
    mvn clean package
    mvn spring-boot:run
```
#### Limitação
 - Sem suporte ao "template links" (por exemplo: http://localhost:8080/book/{id}, id é uma variavel que deve ser preenchida pelo cliente).


Referências:

- https://spring.io/understanding/HATEOAS
- https://dzone.com/articles/building-hateoas-hypermedia
- https://opencredo.com/hal-hypermedia-api-spring-hateoas/
