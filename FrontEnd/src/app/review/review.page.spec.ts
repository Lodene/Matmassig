import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { ReviewPage } from './review.page';

describe('ReviewPage', () => {
  let component: ReviewPage;
  let fixture: ComponentFixture<ReviewPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReviewPage],
      imports: [IonicModule.forRoot(), ExploreContainerComponentModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ReviewPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
