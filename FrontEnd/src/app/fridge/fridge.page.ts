import { Component, OnInit } from '@angular/core';
import { Ingredient } from 'src/models/ingredient';

@Component({
  selector: 'app-fridge',
  templateUrl: 'fridge.page.html',
  styleUrls: ['fridge.page.scss'],
  standalone: false,
})
export class FridgePage implements OnInit {


  results: Ingredient[] = [];
  ingredients : Ingredient[] = []

  constructor() {}
  ngOnInit(): void {
    this.mockUpFridge();
    this.results = [...this.ingredients];
    console.log(this.results)
  }

  mockUpFridge(): void {
    this.ingredients = [
      new Ingredient(0, "carrot", 2, "https://minecraft.wiki/images/Carrot_JE3_BE2.png?5e9c8"),
      new Ingredient(1, "potato", 5, "https://minecraft.wiki/images/Potato_JE3_BE2.png?27685")
    ];
    
  }

}
