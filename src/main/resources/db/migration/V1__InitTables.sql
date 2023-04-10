CREATE SEQUENCE recipe_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE recipe (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    dish_specification VARCHAR(20),
    servings SMALLINT,
    instructions TEXT
);

ALTER TABLE recipe ALTER COLUMN id SET DEFAULT nextval('recipe_seq');
