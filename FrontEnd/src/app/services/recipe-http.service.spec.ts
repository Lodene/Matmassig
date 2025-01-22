import { TestBed } from '@angular/core/testing';

import { RecipeHttpService } from './recipe-http.service';

describe('RecipeHttpService', () => {
  let service: RecipeHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipeHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
