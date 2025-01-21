export class Review {
    id: number;
    email: string;
    comment: string;
    rating: number;
    recipeId: number;
    userId: number;

    constructor(id: number, email: string, title: string, comment: string, rating: number, recipeId: number, userId: number) {
        this.id = id;
        this.email = email;
        this.comment = comment;
        this.rating = rating;
        this.recipeId = recipeId;
        this.userId = userId;
    }
}
