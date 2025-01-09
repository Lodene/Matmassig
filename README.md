# API Documentation

<details>
<summary>## Table of Contents</summary>

1. <details>
   <summary>[Orchestrator Controller](#orchestrator-controller)</summary>
   
   - [Send Recipe](#1-send-recipe)
   </details>
2. <details>
   <summary>[Recipe Controller](#recipe-controller)</summary>

   - [Add Recipe](#1-add-recipe)
   - [Update Recipe](#2-update-recipe)
   - [Delete Recipe](#3-delete-recipe)
   - [Add Ingredient](#4-add-ingredient)
   - [Add Ingredient to Recipe](#5-add-ingredient-to-recipe)
   - [Get Ingredients for Recipe](#6-get-ingredients-for-recipe)
   - [Get All Ingredients](#7-get-all-ingredients)
   - [Get Recipes for User](#8-get-recipes-for-user)
   </details>
3. <details>
   <summary>[Review Controller](#review-controller)</summary>

   - [Get Reviews by User](#1-get-reviews-by-user)
   - [Get Reviews by Recipe](#2-get-reviews-by-recipe)
   - [Add Review](#3-add-review)
   - [Update Review](#4-update-review)
   - [Delete Review](#5-delete-review)
   </details>

</details>

---

<details>
<summary>## Orchestrator Controller</summary>

**Base URL:** `/api/orchestrator`

### 1. Send Recipe
**Endpoint:** `POST /recipe`

**Description:** Sends a recipe along with its ingredients to RabbitMQ.

**Request Parameters:**
- `List<Ingredient>` (Request Param): List of ingredients associated with the recipe.

**Request Body:**
```json
{
  "recipe": {
    "id": 1,
    "title": "Recipe Title",
    "description": "Recipe Description",
    "instructions": "Steps to prepare the recipe",
    "userId": 1
  }
}
```

**Response:**
- `200 OK`: Recipe with ingredients sent to RabbitMQ.

</details>

---

<details>
<summary>## Recipe Controller</summary>

**Base URL:** `/api/recipes`

### 1. Add Recipe
**Endpoint:** `POST /`

**Description:** Creates a new recipe along with its ingredients.

**Request Body:**
```json
{
  "title": "Recipe Title",
  "description": "Recipe Description",
  "ingredientsRecipeId": 1,
  "instructions": "Steps to prepare the recipe",
  "userId": 1,
  "listIngredients": [1, 2, 3]
}
```

**Response:**
- `200 OK`: The created recipe object.

---

### 2. Update Recipe
**Endpoint:** `PUT /{id}`

**Description:** Updates an existing recipe.

**Path Parameter:**
- `id` (Integer): Recipe ID to update.

**Request Body:** Same as Add Recipe.

**Response:**
- `200 OK`: The updated recipe object.

---

### 3. Delete Recipe
**Endpoint:** `DELETE /{id}`

**Description:** Deletes a recipe by its ID.

**Path Parameter:**
- `id` (Integer): Recipe ID to delete.

**Response:**
- `204 No Content`: Recipe deleted.

---

### 4. Add Ingredient
**Endpoint:** `POST /ingredients`

**Description:** Adds a new ingredient.

**Request Body:**
```json
{
  "name": "Ingredient Name",
  "quantity": "100g"
}
```

**Response:**
- `200 OK`: The created ingredient object.

---

### 5. Add Ingredient to Recipe
**Endpoint:** `POST /{recipeId}/ingredients`

**Description:** Links an ingredient to a recipe.

**Path Parameter:**
- `recipeId` (Integer): Recipe ID.

**Request Body:**
```json
{
  "ingredientId": 1,
  "quantity": "200g"
}
```

**Response:**
- `200 OK`: The linked ingredient object.

---

### 6. Get Ingredients for Recipe
**Endpoint:** `GET /{recipeId}/ingredients`

**Description:** Retrieves all ingredients linked to a specific recipe.

**Path Parameter:**
- `recipeId` (Integer): Recipe ID.

**Response:**
- `200 OK`: List of ingredients for the recipe.

---

### 7. Get All Ingredients
**Endpoint:** `GET /ingredients`

**Description:** Retrieves all available ingredients.

**Response:**
- `200 OK`: List of all ingredients.

---

### 8. Get Recipes for User
**Endpoint:** `GET /user/{userId}`

**Description:** Retrieves all recipes created by a specific user.

**Path Parameter:**
- `userId` (Integer): User ID.

**Response:**
- `200 OK`: List of recipes created by the user.

</details>

---

<details>
<summary>## Review Controller</summary>

**Base URL:** `/api/reviews`

### 1. Get Reviews by User
**Endpoint:** `GET /user/{userId}`

**Description:** Retrieves all reviews written by a specific user.

**Path Parameter:**
- `userId` (Integer): User ID.

**Response:**
- `200 OK`: List of reviews by the user.

---

### 2. Get Reviews by Recipe
**Endpoint:** `GET /recipe/{recipeId}`

**Description:** Retrieves all reviews for a specific recipe.

**Path Parameter:**
- `recipeId` (Integer): Recipe ID.

**Response:**
- `200 OK`: List of reviews for the recipe.

---

### 3. Add Review
**Endpoint:** `POST /`

**Description:** Adds a new review.

**Request Body:**
```json
{
  "userId": 1,
  "recipeId": 1,
  "rating": 5,
  "comment": "Excellent recipe!",
  "createdAt": "2024-01-01T10:00:00"
}
```

**Response:**
- `201 Created`: The created review object.

---

### 4. Update Review
**Endpoint:** `PUT /{id}`

**Description:** Updates an existing review.

**Path Parameter:**
- `id` (Integer): Review ID to update.

**Request Body:** Same as Add Review.

**Response:**
- `200 OK`: The updated review object.

---

### 5. Delete Review
**Endpoint:** `DELETE /{id}`

**Description:** Deletes a review by its ID.

**Path Parameter:**
- `id` (Integer): Review ID to delete.

**Response:**
- `204 No Content`: Review deleted.

</details>

