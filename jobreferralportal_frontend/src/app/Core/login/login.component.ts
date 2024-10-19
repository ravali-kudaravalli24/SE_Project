import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'; // Import ReactiveFormsModule
import { HttpClient } from '@angular/common/http'; 
import { Router } from '@angular/router';
import { AuthService } from '../../Services/services/auth.service';
import { SessionService } from '../../Services/services/session.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true, 
  imports: [ReactiveFormsModule,CommonModule], 
  templateUrl: './login.component.html',
  styleUrls:['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService, 
    private router: Router,
    private sessionService: SessionService
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: response => {
          const employeeType = this.sessionService.getSessionData('employeeType');
          if (employeeType === 'HR') {
            this.router.navigate(['/hr-dashboard']); 
          } else {
            this.router.navigate(['/employee-dashboard']);
          }
        },
        error: error => {
          console.error(error);
          this.errorMessage = 'Invalid login credentials';  
        }
      });
    }
  }
  
}
