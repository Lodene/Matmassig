export class Ingredient {
    id: number;
    name: string;
    quantity: number;
    imgSource: string;
    constructor(id: number, name: string, quantity: number, imgSource: string) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imgSource = imgSource;
    }
}