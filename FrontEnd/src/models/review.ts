export class Reviews {
    id: number;
    title: string;
    content: string;
    rating: number;
    constructor(id: number, title: string, content: string, rating: number) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}
