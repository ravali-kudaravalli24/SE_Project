import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferCandidateComponent } from './refer-candidate.component';

describe('ReferCandidateComponent', () => {
  let component: ReferCandidateComponent;
  let fixture: ComponentFixture<ReferCandidateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReferCandidateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReferCandidateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
