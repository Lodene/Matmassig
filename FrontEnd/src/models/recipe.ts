import { Ingredient } from "./ingredient";

export class Recipe {
    id: number;
    title: string;
    description: string;
    ingredients: Ingredient[];
    // number in minutes
    cookTime: number;
    avgReviewScore: number;

    constructor(id: number, title: string, description: string, ingredients: Ingredient[], cookTime: number, avgReviewScore: number) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = [...ingredients];
        this.cookTime = cookTime;
        this.avgReviewScore = avgReviewScore;
    }

}