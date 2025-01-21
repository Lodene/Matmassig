import { TestBed } from '@angular/core/testing';

import { ReviewHttpService } from './review.service';

describe('ReviewService', () => {
  let service: ReviewHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReviewHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
