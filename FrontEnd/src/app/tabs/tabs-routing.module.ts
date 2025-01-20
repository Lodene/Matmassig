import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {
        path: 'review',
        loadChildren: () => import('../review/review.module').then(m => m.ReviewPageModule)
      },
      {
        path: 'recipe',
        loadChildren: () => import('../recipe/recipe.module').then(m => m.RecipePageModule)
      },
      {
        path: 'discover',
        loadChildren: () => import('../discover/discover.module').then(m => m.DiscoverPageModule)
      },
      {
        path: 'fridge',
        loadChildren: () => import('../fridge/fridge.module').then(m => m.FridgePageModule)
      },
      {
        path: '',
        redirectTo: '/tabs/review',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/review',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
