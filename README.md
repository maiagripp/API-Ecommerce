# API-Ecommerce

Grupo do trabalho: Claudia Maia, Cristiny Boeta, Davi Faustino, Luciana Bohrer, Murilo Marinho e Thamires Ramos

Projeto final do módulo de API REST da Residência de Software do Serratec.
O trabalho consiste em uma API para um e-commerce.

Os requisitos foram:
Utilizar um sistema de login.(JWT)
Um Cliente poderá se cadastrar livremente.
Para o cadastro de cliente deverá informar os dados mostrados nas tabelas abaixo. 
O endereço deverá ser validado através da API Via Cep.
Após logado o Cliente poderá fazer as seguintes operações:(Com exceção das duas últimas (11 e 12), todas não poderão ser realizadas sem o envio do token)
Atualizar seus próprios dados pessoais (como endereço, telefone, menos CPF).
Deletar sua própria conta.
Criar um novo Pedido  
Editar um pedido  que não esteja com status de finalizado.
Finalizar um pedido, alterar seu status para finalizado. Ao finalizar o pedido enviar e-mail para o cliente informando data de entrega, produtos, quantidades e valor final do pedido.
Visualizar todas as categorias ou uma específica pelo nome.
Visualizar todos os produtos ou um específico pelo nome.

Recursos que devem estar disponíveis sem o usuário estar logado no sistema:
Visualizar todas as categorias ou uma específica pelo nome.
Criar uma nova categoria.
Editar uma categoria.
Deletar uma categoria
Visualizar todos os produtos ou um específico pelo nome.
Criar um novo produto (Com imagem).
Editar um produto.
Deletar um produto.
Visualizar todos os pedidos.
Excluir algum pedido.

Validações:
CPF deve ser válido.
Produto não poderá ter valores negativos
Todas as exceptions devem ser tratadas
A Api deverá utilizar como documentação a ferramenta do Swagger.

Desafio Extra (Opcional):
Criar uma opção de esqueci minha senha com envio de um código de verificação para o e-mail e posterior verificação se esse código pertence ao cliente.
