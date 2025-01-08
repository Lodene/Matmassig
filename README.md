# API Documentation - InventoryMicroService

## **Endpoints**

<details>
<summary>### **1. Ajouter un item**</summary>

- **Méthode** : `POST`  
- **URL** : `/api/inventory`  
- **Body** :  
  ```json
  {
      "ingredientId": 1,
      "quantity": 100,
      "userId": 10
  }
  ```
- **Réponse (200 OK)** :  
  ```json
  {
      "id": 1,
      "ingredientId": 1,
      "quantity": 100,
      "userId": 10,
      "createdAt": "2025-01-01T12:00:00",
      "updatedAt": "2025-01-01T12:00:00"
  }
  ```

</details>

---

<details>
<summary>### **2. Mettre à jour un item**</summary>

- **Méthode** : `PUT`  
- **URL** : `/api/inventory/{id}`  
- **Body** :  
  ```json
  {
      "ingredientId": 2,
      "quantity": 150,
      "userId": 10
  }
  ```
- **Réponse (200 OK)** :  
  ```json
  {
      "id": 1,
      "ingredientId": 2,
      "quantity": 150,
      "userId": 10,
      "createdAt": "2025-01-01T12:00:00",
      "updatedAt": "2025-01-01T12:05:00"
  }
  ```

</details>

---

<details>
<summary>### **3. Supprimer un item**</summary>

- **Méthode** : `DELETE`  
- **URL** : `/api/inventory/{id}`  
- **Réponse (204 No Content)** :  
  Pas de contenu (supprimé avec succès).

</details>

---

<details>
<summary>### **4. Lister les items d’un utilisateur**</summary>

- **Méthode** : `GET`  
- **URL** : `/api/inventory/user/{userId}`  
- **Réponse (200 OK)** :  
  ```json
  [
      {
          "id": 1,
          "ingredientId": 1,
          "quantity": 100,
          "userId": 10,
          "createdAt": "2025-01-01T12:00:00",
          "updatedAt": "2025-01-01T12:00:00"
      },
      {
          "id": 2,
          "ingredientId": 3,
          "quantity": 50,
          "userId": 10,
          "createdAt": "2025-01-01T13:00:00",
          "updatedAt": "2025-01-01T13:00:00"
      }
  ]
  ```

</details>

<details>
<summary>Table des matières</summary>

- [Configuration](#configuration)
- [Endpoints](#endpoints)
  - [Ajouter une recette](#ajouter-une-recette)
  - [Modifier une recette](#modifier-une-recette)
  - [Supprimer une recette](#supprimer-une-recette)
</details>

---

## Configuration

Pour exécuter ce projet localement :

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/example/recipe-service.git
   ```
2. Configurez les informations de base de données dans `application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/recipe_service
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Lancez l'application Spring Boot.

---

<details>
<summary>Table des matières</summary>

- [Configuration](#configuration)
- [Endpoints](#endpoints)
  - [Ajouter une recette](#ajouter-une-recette)
  - [Modifier une recette](#modifier-une-recette)
  - [Supprimer une recette](#supprimer-une-recette)
  - [Ajouter un ingrédient](#ajouter-un-ingrédient)
  - [Associer un ingrédient à une recette](#associer-un-ingrédient-à-une-recette)
  - [Lister les ingrédients d'une recette](#lister-les-ingrédients-dune-recette)
</details>

---

## Configuration

Pour exécuter ce projet localement :

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/example/recipe-service.git
   ```
2. Configurez les informations de base de données dans `application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/recipe_service
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Lancez l'application Spring Boot.

---

### Ajouter une recette

**POST** `/api/recipes`

Crée une nouvelle recette.

**Corps de la requête (JSON)** :
```json
{
  "title": "Spaghetti Bolognese",
  "description": "Une recette classique italienne",
  "ingredientsRecipeId": 1,
  "instructions": "Faites cuire les pâtes et mélangez-les avec la sauce",
  "userId": 123,
  "listIngredients": [2, 5, 13, 30]
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "title": "Spaghetti Bolognese",
  "description": "Une recette classique italienne",
  "ingredientsRecipeId": 1,
  "instructions": "Faites cuire les pâtes et mélangez-les avec la sauce",
  "userId": 123,
  "createdAt": "2025-01-08T14:45:00",
  "updatedAt": "2025-01-08T14:45:00"
}
```

---

### Modifier une recette

**PUT** `/api/recipes/{id}`

Met à jour une recette existante.

**Paramètres d'URL** :
- `id` : L'identifiant unique de la recette à mettre à jour.

**Corps de la requête (JSON)** :
```json
{
  "title": "Spaghetti Carbonara",
  "description": "Une autre recette classique italienne",
  "ingredientsRecipeId": 2,
  "instructions": "Ajoutez des œufs, du bacon et du fromage",
  "userId": 123,
  "listIngredients": [3, 8, 14, 32]
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "title": "Spaghetti Carbonara",
  "description": "Une autre recette classique italienne",
  "ingredientsRecipeId": 2,
  "instructions": "Ajoutez des œufs, du bacon et du fromage",
  "userId": 123,
  "createdAt": "2025-01-08T14:45:00",
  "updatedAt": "2025-01-08T15:00:00"
}
```

---

# Recipe-Service API Documentation

Cette documentation décrit les endpoints exposés par le microservice Recipe-Service pour la gestion des recettes et leurs associations avec les ingrédients.

---

## Endpoints

### Ajouter une recette

**POST** `/api/recipes`

Crée une nouvelle recette.

**Corps de la requête (JSON)** :
```json
{
  "title": "Spaghetti Bolognese",
  "description": "Une recette classique italienne",
  "ingredientsRecipeId": 1,
  "instructions": "Faites cuire les pâtes et mélangez-les avec la sauce",
  "userId": 123,
  "listIngredients": [2, 5, 13, 30]
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "title": "Spaghetti Bolognese",
  "description": "Une recette classique italienne",
  "ingredientsRecipeId": 1,
  "instructions": "Faites cuire les pâtes et mélangez-les avec la sauce",
  "userId": 123,
  "createdAt": "2025-01-08T14:45:00",
  "updatedAt": "2025-01-08T14:45:00"
}
```

---

### Modifier une recette

**PUT** `/api/recipes/{id}`

Met à jour une recette existante.

**Paramètres d'URL** :
- `id` : L'identifiant unique de la recette à mettre à jour.

**Corps de la requête (JSON)** :
```json
{
  "title": "Spaghetti Carbonara",
  "description": "Une autre recette classique italienne",
  "ingredientsRecipeId": 2,
  "instructions": "Ajoutez des œufs, du bacon et du fromage",
  "userId": 123,
  "listIngredients": [3, 8, 14, 32]
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "title": "Spaghetti Carbonara",
  "description": "Une autre recette classique italienne",
  "ingredientsRecipeId": 2,
  "instructions": "Ajoutez des œufs, du bacon et du fromage",
  "userId": 123,
  "createdAt": "2025-01-08T14:45:00",
  "updatedAt": "2025-01-08T15:00:00"
}
```

---

### Supprimer une recette

**DELETE** `/api/recipes/{id}`

Supprime une recette existante.

**Paramètres d'URL** :
- `id` : L'identifiant unique de la recette à supprimer.

**Réponse (204)** :
Aucune donnée renvoyée.

---

### Ajouter un ingrédient

**POST** `/api/recipes/ingredients`

Ajoute un nouvel ingrédient.

**Corps de la requête (JSON)** :
```json
{
  "name": "Tomates"
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "name": "Tomates"
}
```

---

### Lister tous les ingrédients

**GET** `/api/recipes/ingredients`

Récupère la liste de tous les ingrédients disponibles.

**Réponse (200)** :
```json
[
  {
    "id": 1,
    "name": "Tomates"
  },
  {
    "id": 2,
    "name": "Oignons"
  }
]
```

---

### Associer un ingrédient à une recette

**POST** `/api/recipes/{recipeId}/ingredients`

Associe un ingrédient existant à une recette.

**Paramètres d'URL** :
- `recipeId` : L'identifiant unique de la recette.

**Corps de la requête (JSON)** :
```json
{
  "ingredientId": 1,
  "quantity": 3
}
```

**Réponse (200)** :
```json
{
  "id": 1,
  "ingredientId": 1,
  "quantity": 3,
  "recipeId": 1
}
```

---

### Lister les ingrédients d'une recette

**GET** `/api/recipes/{recipeId}/ingredients`

Récupère les ingrédients associés à une recette spécifique.

**Paramètres d'URL** :
- `recipeId` : L'identifiant unique de la recette.

**Réponse (200)** :
```json
[
  {
    "id": 1,
    "ingredientId": 1,
    "quantity": 3,
    "recipeId": 1
  },
  {
    "id": 2,
    "ingredientId": 2,
    "quantity": 2,
    "recipeId": 1
  }
]
```

---

### Lister toutes les recettes et leurs ingrédients pour un utilisateur

**GET** `/api/recipes/user/{userId}`

Récupère toutes les recettes d'un utilisateur spécifique avec les ingrédients associés.

**Paramètres d'URL** :
- `userId` : L'identifiant unique de l'utilisateur.

**Réponse (200)** :
```json
[
  {
    "id": 1,
    "title": "Spaghetti Bolognese",
    "description": "Une recette classique italienne",
    "instructions": "Faites cuire les pâtes et mélangez-les avec la sauce",
    "createdAt": "2025-01-08T14:45:00",
    "updatedAt": "2025-01-08T14:45:00",
    "ingredients": [
      {
        "id": 2,
        "name": "Oignons",
        "quantity": 3
      },
      {
        "id": 5,
        "name": "Carottes",
        "quantity": 2
      }
    ]
  },
  {
    "id": 2,
    "title": "Salade César",
    "description": "Une salade classique",
    "instructions": "Mélangez tous les ingrédients et servez frais",
    "createdAt": "2025-01-07T10:00:00",
    "updatedAt": "2025-01-07T10:30:00",
    "ingredients": [
      {
        "id": 8,
        "name": "Laitue",
        "quantity": 1
      },
      {
        "id": 9,
        "name": "Croutons",
        "quantity": 5
      }
    ]
  }
]
```
