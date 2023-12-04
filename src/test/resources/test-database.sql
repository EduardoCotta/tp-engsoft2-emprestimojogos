-- Create the user table
CREATE TABLE IF NOT EXISTS account (
                      email VARCHAR(255) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      interests VARCHAR(255),
                      password VARCHAR(255) NOT NULL
);

-- Create the game table
CREATE TABLE IF NOT EXISTS game (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      price DOUBLE PRECISION NOT NULL,
                      ownerEmail VARCHAR(255) REFERENCES account(email) ON DELETE CASCADE
);

-- Create the loan table
CREATE TABLE IF NOT EXISTS loan (
                      deadline DATE NOT NULL,
                      ownerEmail VARCHAR(255) REFERENCES account(email) ON DELETE CASCADE,
                      requestUserEmail VARCHAR(255) REFERENCES account(email) ON DELETE CASCADE,
                      gameId UUID REFERENCES game(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO account (email, name, interests, password) VALUES
                                                        ('user1@example.com', 'User 1', 'Gaming', 'password123'),
                                                        ('user2@example.com', 'User 2', 'Movies', 'securepass');

INSERT INTO game (id, name, description, price, ownerEmail) VALUES
                                                                (UUID('550e8400-e29b-41d4-a716-446655440000'), 'Game A', 'Action game', 29.99, 'user1@example.com'),
                                                                (UUID('550e8400-e29b-41d4-a716-446655440001'), 'Game B', 'Adventure game', 19.99, 'user2@example.com');

INSERT INTO loan (deadline, ownerEmail, requestUserEmail, gameId) VALUES
                                                                      ('2023-12-31', 'user1@example.com', 'user2@example.com', UUID('550e8400-e29b-41d4-a716-446655440000')),
                                                                      ('2023-11-30', 'user2@example.com', 'user1@example.com', UUID('550e8400-e29b-41d4-a716-446655440001'));

SELECT * FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';