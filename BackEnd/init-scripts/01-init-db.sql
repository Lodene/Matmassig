-- Création des tables dans le bon ordre

-- Table Ingredient
CREATE TABLE Ingredients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Table User
CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Recipe
CREATE TABLE Recipes (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    ingredients_recipe_id Integer,
    instructions TEXT,
    user_id Integer NOT NULL REFERENCES "User"(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table IngredientsRecipe
CREATE TABLE IngredientsRecipe (
    id SERIAL PRIMARY KEY,
    ingredient_id Integer NOT NULL REFERENCES Ingredients(id),
    recipe_id Integer NOT NULL REFERENCES Recipes(id),
    quantity Integer NOT NULL
);

-- Table UserPreferences
CREATE TABLE UserPreferences (
    id SERIAL PRIMARY KEY,
    user_id Integer NOT NULL REFERENCES "User"(id),
    note Integer,
    recipe_id Integer NOT NULL REFERENCES Recipes(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Review
CREATE TABLE Review (
    id SERIAL PRIMARY KEY,
    recipe_id Integer NOT NULL REFERENCES Recipes(id),
    user_id Integer NOT NULL REFERENCES "User"(id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Inventory_Item
CREATE TABLE Inventory_Item (
    id SERIAL PRIMARY KEY,
    ingredient_id INT NOT NULL REFERENCES Ingredients(id),
    quantity INT NOT NULL,
    user_id INT NOT NULL REFERENCES "User"(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Notification
CREATE TABLE Notification (
    id SERIAL PRIMARY KEY,
    user_id Integer NOT NULL REFERENCES "User"(id),
    message TEXT NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent_at TIMESTAMP
);

-- Table UserRecipeIA
CREATE TABLE UserRecipeIA (
    id SERIAL PRIMARY KEY,
    user_id Integer NOT NULL REFERENCES "User"(id),
    recipeIA_id Integer NOT NULL,
    status BOOLEAN NOT NULL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

