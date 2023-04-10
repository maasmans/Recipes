ALTER TABLE ingredient ADD COLUMN recipe_id BIGSERIAL;

ALTER TABLE ingredient
ADD CONSTRAINT fk_ingredient_recipe
FOREIGN KEY (recipe_id)
REFERENCES recipe(id);