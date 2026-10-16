import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';
import { mustHaveBookGuard } from './must-have-book-guard';

describe('mustHaveBookGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => mustHaveBookGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
