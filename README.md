# Spring HATEOAS

Este é um projeto simples que demonstra como o Spring HATEOAS funciona.

Link oficial da ferramenta: http://projects.spring.io/spring-hateoas/

#### O que é o HATEOAS?
HATEOAS (Hypermidia as the Engine of Application State) é uma abordagem para a construção de web services RESTful onde o cliente pode descobrir dinamicamente as ações disponíveis a ele no servidor em tempo de execução. As opções de ações disponíveis podem ser conduzidos por estados no tempo (por exemplo, diferentes ações disponíveis para o usuário de acordo com o controle de acesso) ou por funcionalidades disponíveis (por exemplo, uma nova funcionalidade fica disponível).

O exemplo abaixo mostra o objeto JSON que representa um livro em uma biblioteca. Temos as informações, os links que representam as informações e a ação de empresar o livro.
```json
{
   "id": 1,
   "title": "Book One",
   "author":    {
      "id": 1,
      "name": "Author X"
   },
   "stockLevel": 2,
   "_links":    {
      "self":       [
         {"href": "http://localhost:8080/book/1"},
         {"href": "http://localhost:8080/author/1"}
      ],
      "book.borrow": {"href": "http://localhost:8080/book/borrow/1"}
   }
}
```

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

#### Limitação
 - Sem suporte ao "template links" (por exemplo: http://localhost:8080/book/{id}, id é uma variavel que deve ser preenchida pelo cliente).

### Executando o projeto:

Para executar o exemplo do projeto basta clocar o repositório e rodar os comandos via terminal dentro da pasta do projeto:

```sh
$ mvn clean package
$ mvn spring-boot:run
```



Referências:

- https://spring.io/understanding/HATEOAS
- https://dzone.com/articles/building-hateoas-hypermedia
- https://opencredo.com/hal-hypermedia-api-spring-hateoas/
