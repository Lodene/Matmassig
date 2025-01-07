-- Create Ingredient table (doit être créé en premier pour éviter l'erreur de dépendance)
CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Create User table
CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Recipe table
CREATE TABLE Recipe (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    ingredients_recipe_id BIGINT,
    instructions TEXT,
    created_by BIGINT NOT NULL REFERENCES "User"(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create IngredientsRecipe table (références Ingredient, donc doit venir après)
CREATE TABLE IngredientsRecipe (
    id SERIAL PRIMARY KEY,
    ingredient_id BIGINT NOT NULL REFERENCES Ingredient(id),
    quantity BIGINT NOT NULL
);

-- Create UserPreferences table
CREATE TABLE UserPreferences (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "User"(id),
    note BIGINT,
    recipe_id BIGINT NOT NULL REFERENCES Recipe(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Review table
CREATE TABLE Review (
    id SERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL REFERENCES Recipe(id),
    user_id BIGINT NOT NULL REFERENCES "User"(id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create InventoryItem table
CREATE TABLE InventoryItem (
    id SERIAL PRIMARY KEY,
    ingredient_id BIGINT NOT NULL REFERENCES Ingredient(id),
    quantity BIGINT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES "User"(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Notification table
CREATE TABLE Notification (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "User"(id),
    message TEXT NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent_at TIMESTAMP
);
