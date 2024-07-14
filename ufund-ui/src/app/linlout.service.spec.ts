import { TestBed } from '@angular/core/testing';

import { LinloutService } from './linlout.service';

describe('LinloutService', () => {
  let service: LinloutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LinloutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
