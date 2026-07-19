-- Autor
INSERT INTO autor (id_autor, nome_autor, nacionalidade)
SELECT 'AU001', 'Machado de Assis', 'Brasileiro'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE id_autor = 'AU001')
ON CONFLICT DO NOTHING;

INSERT INTO autor (id_autor, nome_autor, nacionalidade)
SELECT 'AU002', 'Clarice Lispector', 'Brasileiro'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE id_autor = 'AU002')
ON CONFLICT DO NOTHING;

INSERT INTO autor (id_autor, nome_autor, nacionalidade)
SELECT 'AU003', 'Jorge Amado', 'Brasileiro'
WHERE NOT EXISTS (SELECT 1 FROM autor WHERE id_autor = 'AU003')
ON CONFLICT DO NOTHING;

-- Editora
INSERT INTO editora (nome, prefixo_editorial)
SELECT 'Editora Saraiva', '978-85-02'
WHERE NOT EXISTS (SELECT 1 FROM editora WHERE nome = 'Editora Saraiva')
ON CONFLICT DO NOTHING;

INSERT INTO editora (nome, prefixo_editorial)
SELECT 'Companhia das Letras', '978-85-359'
WHERE NOT EXISTS (SELECT 1 FROM editora WHERE nome = 'Companhia das Letras')
ON CONFLICT DO NOTHING;

-- Livro
INSERT INTO livro (titulo, autor_id, isbn, editora_id, quant, traducao, volume, classindicativa)
SELECT 'Dom Casmurro', 1, '978-85-02-06802-2', 1, 5, 'Nenhuma', 1, 'Livre'
WHERE NOT EXISTS (SELECT 1 FROM livro WHERE isbn = '978-85-02-06802-2')
ON CONFLICT DO NOTHING;

INSERT INTO livro (titulo, autor_id, isbn, editora_id, quant, traducao, volume, classindicativa)
SELECT 'A Hora da Estrela', 2, '978-85-359-0120-5', 2, 3, 'Nenhuma', 1, 'Livre'
WHERE NOT EXISTS (SELECT 1 FROM livro WHERE isbn = '978-85-359-0120-5')
ON CONFLICT DO NOTHING;

INSERT INTO livro (titulo, autor_id, isbn, editora_id, quant, traducao, volume, classindicativa)
SELECT 'Gabriela, Cravo e Canela', 3, '978-85-359-0048-2', 2, 4, 'Nenhuma', 1, 'Livre'
WHERE NOT EXISTS (SELECT 1 FROM livro WHERE isbn = '978-85-359-0048-2')
ON CONFLICT DO NOTHING;

-- Endereco
INSERT INTO endereco (logradouro, numero, bairro, cep)
SELECT 'Rua das Flores', '10', 'Centro', '45100-000'
WHERE NOT EXISTS (SELECT 1 FROM endereco WHERE cep = '45100-000' AND numero = '10')
ON CONFLICT DO NOTHING;

INSERT INTO endereco (logradouro, numero, bairro, cep)
SELECT 'Avenida Principal', '200', 'Jardim', '45100-001'
WHERE NOT EXISTS (SELECT 1 FROM endereco WHERE cep = '45100-001' AND numero = '200')
ON CONFLICT DO NOTHING;

-- Pessoa/Bibliotecario
-- Senha: 'admin123' criptografada com BCrypt
INSERT INTO pessoa (nome, telefone, cpf, email, senha)
SELECT 'Maria Silva', '(77) 99999-0001', '111.111.111-11', 'maria@biblioteca.com', '$2a$10$7QViGqKQbH5G1ZsM9XR5/.K1L0U5VyexBz4fhJIAXfCaGMjzRbS9i'
WHERE NOT EXISTS (SELECT 1 FROM pessoa WHERE email = 'maria@biblioteca.com')
ON CONFLICT DO NOTHING;

INSERT INTO bibliotecario (id, matricula)
SELECT id, 'BIB001' FROM pessoa WHERE email = 'maria@biblioteca.com'
AND NOT EXISTS (SELECT 1 FROM bibliotecario WHERE matricula = 'BIB001')
ON CONFLICT DO NOTHING;

-- Pessoa/Usuario
-- Senha: 'user123' criptografada com BCrypt
INSERT INTO pessoa (nome, telefone, cpf, email, senha)
SELECT 'João Santos', '(77) 99999-0002', '222.222.222-22', 'joao@email.com', '$2a$10$8cQ0VVtHmVHKQ5Kq9KTXxOX4nDjqAXKwJzXsXnXoXpXqXrXsXtXuX'
WHERE NOT EXISTS (SELECT 1 FROM pessoa WHERE email = 'joao@email.com')
ON CONFLICT DO NOTHING;

INSERT INTO usuario (id, qtd_livros, elegivel_para_fazer_emprestimo, endereco_id)
SELECT p.id, 0, true, e.id
FROM pessoa p, endereco e
WHERE p.email = 'joao@email.com'
AND e.cep = '45100-001'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id = p.id)
ON CONFLICT DO NOTHING;