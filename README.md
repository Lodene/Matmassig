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
