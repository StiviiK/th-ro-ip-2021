import { TestBed } from '@angular/core/testing';

import { BibtexService } from './bibtex.service';

describe('BibtexService', () => {
  let service: BibtexService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BibtexService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
