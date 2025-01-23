export class Ingredient {
    id: number;
    name: string;
    quantity: number;
    imgSource: string;
    userId: number;
    constructor(id: number, name: string, quantity: number, imgSource?: string, userId?: number) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imgSource = imgSource || '';
        this.userId = userId || -1;
    }
}