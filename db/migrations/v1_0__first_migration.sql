CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) DEFAULT 'system',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) DEFAULT 'system',
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(50),
    CONSTRAINT role_check CHECK (role IN ('ADMIN', 'USER', 'GUEST'))
);

CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) DEFAULT 'system',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) DEFAULT 'system',
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    post_id INT REFERENCES posts(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) DEFAULT 'system',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) DEFAULT 'system',
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(50)
);

INSERT INTO users (username, email, password_hash, role) VALUES
('admin', 'email', '$2b$12$KIXQJZ5E6j1y5Z9Z8e4OeO7y5jF6j1y5Z9e4OeO7y5jF6j1y5Z9e4O', 'ADMIN')
    ON CONFLICT (username) DO NOTHING,
('user', 'email', '$2b$12$KIXQJZ5E6j1y5Z9Z8e4OeO7y5jF6j1y5Z9e4OeO7y5jF6j1y5Z9e4O', 'USER')
    ON CONFLICT (username) DO NOTHING,
('guest', 'email', '$2b$12$KIXQJZ5E6j1y5Z9Z8e4OeO7y5jF6j1y5Z9e4OeO7y5jF6j1y5Z9e4O', 'GUEST')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO posts (user_id, title, content) VALUES
((SELECT id FROM users WHERE username='admin'), 'Welcome to the Blog', 'This is the first post on the blog!')
ON CONFLICT (title) DO NOTHING;

INSERT INTO comments (post_id, user_id, content) VALUES
((SELECT id FROM posts WHERE title='Welcome to the Blog'), (SELECT id FROM users WHERE username='admin'), 'This is a comment on the first post.')
ON CONFLICT (content) DO NOTHING;