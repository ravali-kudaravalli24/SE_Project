import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferredCandidatesComponent } from './referred-candidates.component';

describe('ReferredCandidatesComponent', () => {
  let component: ReferredCandidatesComponent;
  let fixture: ComponentFixture<ReferredCandidatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReferredCandidatesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReferredCandidatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
