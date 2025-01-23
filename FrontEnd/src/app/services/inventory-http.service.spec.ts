import { TestBed } from '@angular/core/testing';

import { InventoryHttpService } from './inventory-http.service';

describe('InventoryHttpService', () => {
  let service: InventoryHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InventoryHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
