CREATE TABLE ingredient (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    inclusion VARCHAR(20) NOT NULL,
    recipe_id BIGSERIAL REFERENCES recipe (id) NOT NULL
);