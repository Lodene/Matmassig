import { TestBed } from '@angular/core/testing';

import { DiscoveryHttpService } from './discovery-http.service';

describe('DiscoveryHttpService', () => {
  let service: DiscoveryHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiscoveryHttpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
