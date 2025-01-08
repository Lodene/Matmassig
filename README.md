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
  "createdBy": 123
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
  "createdBy": 123,
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
  "createdBy": 123
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
  "createdBy": 123,
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

## Notes

- Les erreurs sont retournées sous le format suivant :
```json
{
  "timestamp": "2025-01-08T14:50:00",
  "message": "Recipe not found",
  "status": 404
}
```
- L'API utilise le format ISO 8601 pour les dates (`YYYY-MM-DDTHH:mm:ss`).

Pour toute question ou problème, veuillez contacter l'équipe de développement à support@example.com.

