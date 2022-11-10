# Desafio API Texo IT
Api referente ao desafio da Texo IT

Projeto desenvolvido com Spring Tool Suite 4

## Instruções de execução:
- Baixar repositório localmente e importar no Spring Tool Suite no menu File, opção Open Projects from File System.
- O arquivo csv contendo os dados, deve estar na pasta arquivoCsv dentro da raiz do projeto, caso queira testar com um arquivo de outro diretório, o caminho deve ser alterado no arquivo application.properties.

## Testes:
No repositório contém um arquivo chamado testes_texoit-api.postman_collection.json.<br />
Esse arquivo possui testes realizados com as transações da API no software Postman, podendo ser importado nele para repetir os testes das transações.

## Transação Principal:
#### - pesquisar <br />
- method: GET <br />
- url: localhost:8080/api/movies/pesquisar <br />
- result: Retorna a solicitação do desafio, ou seja, a lista dos produtores que tiveram menor tempo entre dois prêmios consecutivos, e uma lista com os produtores que tiveram o maior tempo de intervalo entre dois prêmios consecutivos.

## Transações extras:
#### - listar <br />
- method: GET <br />
- url: localhost:8080/movies <br />
- result: Lista com todos os objetos JSON dos filmes carregados do arquivo CSV na inicialização do projeto.

#### - consultarPorId <br />
- method: GET <br />
- url: localhost:8080/movies/{id} <br />
- result: Objeto JSON com os dados do filme consultado pelo id.

#### - adicionar <br />
- method: POST <br />
- url: localhost:8080/api/movies <br />
- body: Objeto JSON com os dados de um filme para ser adicionado. <br />
`{` <br />
`"ano": 2022,`<br />
`"titulo": "Título do Filme",` <br />
`"estudio": "Estudio do Filme",` <br />
`"produtor": "Nome do Produtor",` <br />
`"vencedor": true/false` <br />
`}` <br />
- result: O próprio Objeto JSON informado no body, mas com o acréscimo do atributo "id" gerado automaticamente na inclusão dos dados.

#### - deletar <br />
- method: DELETE <br />
- url: localhost:8080/api/movies/{id} <br />
- result: Não retorna informações, somente status 200 - OK.



