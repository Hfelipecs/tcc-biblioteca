-- Autor
INSERT INTO autor (idautor, nome_autor, nacionalidade)
VALUES ('AU001', 'Machado de Assis', 'Brasileiro')
ON CONFLICT DO NOTHING;

INSERT INTO autor (idautor, nome_autor, nacionalidade)
VALUES ('AU002', 'Clarice Lispector', 'Brasileiro')
ON CONFLICT DO NOTHING;

-- Editora
INSERT INTO editora (nome, prefixo_editorial)
VALUES ('Editora Saraiva', '978-85-02')
ON CONFLICT DO NOTHING;

-- Livro
INSERT INTO livro (titulo, isbn, quant, traducao, volume, classindicativa, autor_id, editora_id)
VALUES ('Dom Casmurro', '978-85-02-06802-2', 5, 'Nenhuma', 1, 'Livre',
    (SELECT id FROM autor WHERE idautor = 'AU001'),
    (SELECT id FROM editora WHERE nome = 'Editora Saraiva'))
ON CONFLICT DO NOTHING;

-- Endereco
INSERT INTO endereco (logradouro, numero, bairro, cep)
VALUES ('Rua das Flores', '10', 'Centro', '45100-000')
ON CONFLICT DO NOTHING;

INSERT INTO endereco (logradouro, numero, bairro, cep)
VALUES ('Avenida Principal', '200', 'Jardim', '45100-001')
ON CONFLICT DO NOTHING;

-- Bibliotecario - Senha: admin123
INSERT INTO pessoa (nome, telefone, cpf, email, senha)
VALUES ('Maria Silva', '(77) 99999-0001', '111.111.111-11', 'maria@biblioteca.com',
    '$2a$10$7QViGqKQbH5G1ZsM9XR5/.K1L0U5VyexBz4fhJIAXfCaGMjzRbS9i')
ON CONFLICT DO NOTHING;

INSERT INTO bibliotecario (id, matricula)
SELECT id, 'BIB001' FROM pessoa WHERE email = 'maria@biblioteca.com'
ON CONFLICT DO NOTHING;

-- Usuario - Senha: user123
INSERT INTO pessoa (nome, telefone, cpf, email, senha)
VALUES ('Joao Santos', '(77) 99999-0002', '222.222.222-22', 'joao@email.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh7y')
ON CONFLICT DO NOTHING;

INSERT INTO usuario (id, qtd_livros, elegivel_para_fazer_emprestimo, endereco_id)
SELECT p.id, 0, true, e.id
FROM pessoa p, endereco e
WHERE p.email = 'joao@email.com'
AND e.cep = '45100-001'
ON CONFLICT DO NOTHING;