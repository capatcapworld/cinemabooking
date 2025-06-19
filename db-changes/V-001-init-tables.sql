CREATE TABLE show (
    id SERIAL PRIMARY KEY,
    movie_name VARCHAR(100) DEFAULT NULL,
    running_date timestamp DEFAULT NULL,
    created timestamp NOT NULL DEFAULT NOW()
);
