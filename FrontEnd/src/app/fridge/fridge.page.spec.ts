import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { FridgePage } from './fridge.page';

describe('FridgePage', () => {
  let component: FridgePage;
  let fixture: ComponentFixture<FridgePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FridgePage],
      imports: [IonicModule.forRoot(), ExploreContainerComponentModule]
    }).compileComponents();

    fixture = TestBed.createComponent(FridgePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
