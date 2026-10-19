-- Unidade utilizada nos testes do Postman
INSERT INTO unidade (id, endereco, nome)
VALUES (1, 'Rua Principal, 100', 'Unidade Centro')
ON DUPLICATE KEY UPDATE id = id;
-- Produto utilizado nos testes do Postman
INSERT INTO produto (id, nome, preco, validade)
VALUES (1, 'Coxinha de Frango', 8.50, '2026-12-31')
ON DUPLICATE KEY UPDATE id = id;
-- Estoque inicial do produto na unidade
INSERT INTO estoque_produto (id, quantidade, produto_id, unidade_id)
VALUES (1, 10, 1, 1)
ON DUPLICATE KEY UPDATE id = id;
-- Usuário utilizado nos testes do Postman
INSERT INTO usuario (id, nome, email, cpf, senha, role)
VALUES (
    1,
    'Usuario Teste',
    'teste@teste.com',
    NULL,
    '$2a$10$yppESsQjSp6JTjC/wElkAu3dNTH8MulTNRoarSP6waaFasynr1Dqq',
    'CLIENTE'
)
ON DUPLICATE KEY UPDATE id = id;