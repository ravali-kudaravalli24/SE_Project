import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmployeeService } from '../../Services/services/employee.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-refer-candidate',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './refer-candidate.component.html',
  styleUrls: ['./refer-candidate.component.css']
})
export class ReferCandidateComponent implements OnInit {
  jobId!: number;
  referForm: FormGroup;
  resumeFile: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router
  ) {
    this.referForm = this.fb.group({
      fName: ['', Validators.required],
      lName: ['', Validators.required],
      yearsOfExp: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.jobId = Number(this.route.snapshot.paramMap.get('jobId'));
  }

  onFileChange(event: any): void {
    this.resumeFile = event.target.files[0];
  }

  onSubmit(): void {
    if (this.referForm.valid && this.resumeFile) {
      const { fName, lName, yearsOfExp } = this.referForm.value;
      const referredBy = parseInt(sessionStorage.getItem('employeeID') || '0', 10);
      this.employeeService.referCandidate(this.jobId, fName, lName, yearsOfExp, referredBy, this.resumeFile)
  .subscribe({
    next: (response) => {
      if (response.status === 200) {
        alert(response.body); // Show the response message
        this.router.navigate(['/employee-dashboard']);
      } else {
        console.error('Unexpected status code:', response.status);
      }
    },
    error: (error) => {
      console.error('Error referring candidate:', error);
    }
  });

  }
}
}
