# Chat Multicast

O programa apresentado consciste em uma aplicação java que utiliza da biblioteca java.net para fazer conexoes UDP entre uma classe Cliente e uma Classe Servidor. É possível também fazer uma conexão multicast peer to peer entre clientes para fazer um chat de texto entre os mesmos.

É apresentado no programa uma Classe Cliente para fazer que permite ao usuário criar uma sala de chat multicast, ver as salas existetentes com seus participantes, entrar em uma sala de chat multicast. Fazendo assim uma conexão com o servidor para buscar os dados.

## Autor

* [Thiago Jorge Queiroz Silva](https://github.com/ThiagoQueirozSilva)

## Orientador

* Hugo Bastos de Paula

## Instruções de utilização

Para se iniciar o programa entre na pasta bin do programa pelo terminal e digite o comando "java redes.Server" para entao rodar o servidor.
Para rodar o cliente basta entrar na pasta bin e digitar o comando "java redes.Client localhost" em um outro terminal, pode-se rodar varios clientes de uma vez mas apenas um servidor. Apos inicial a aplicacao basta seguir as instruções que serão dadas dentro do programa.


## Protocolo

CREATE - comando que é passado ao servido para criar uma sala de chat multicast.
JOIN ROOM - comando que é passado ao servidor para adicionar um usuário a uma sala de chat multicast.
LIST ROOMS - comando que é passado ao servido para listar a lista de salas disponiveis junto aos seus participantes.
EXIT - comando passado para encerrar a aplicacao cliente.
LEAVE ROOM - comando passado para retirar um usuário de uma sala de chat.
MESSAGE - se estiver dentro da sala de multicast o usuario pode enviar mensagens para os outros participantes.

