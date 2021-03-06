## Desafio Volanty
O desafio consiste em expor APIs Rest capazes de gerenciar CAVs e agendamentos de veículos para visita e inspeção.

## Projeto
### Subindo o ambiente
Para subir a aplicação e banco em docker é necessário:

* Criar o caminho ~/desafio/docker/scripts e copiar o arquivo 'dump.sql' disponível em src/main/config/bd para ele
```sh
$ mkdir -p ~/desafio/docker/scripts
$ cd ~/desafio/docker/scripts
$ cp <caminho_projeto>/src/main/config/bd/dump.sql ./
```
* Estando na raiz do projeto, caminhar até src/main/config/docker para subir o docker compose
```sh
$ cd src/main/config/docker
$ docker-compose up -d
```
* Dessa forma, a aplicação sobe localmente numa porta 8089, o banco na porta 3306 e o adminer (console administrador do banco) na porta 8080. Para acessar o console e verificar persistência dos dados, basta abrir no http://localhost:8080/ com configuração:
servidor = 127.0.0.1:3306 | base de dados = desafio | usuário = root | senha = secret-pw
