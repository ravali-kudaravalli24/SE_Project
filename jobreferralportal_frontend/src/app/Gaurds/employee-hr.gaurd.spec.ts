import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { employeeHrGuard } from './employee-hr.gaurd';

describe('employeeHrGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => employeeHrGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
