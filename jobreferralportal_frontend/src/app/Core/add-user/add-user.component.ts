import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../Services/services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';  // Import Router

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule]
})
export class AddUserComponent implements OnInit {
  addUserForm: FormGroup;
  message: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { // Inject Router
    this.addUserForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone_number: ['', Validators.required],
      role: ['', Validators.required],
      position: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      verifyPassword: ['', Validators.required],
      score: [0],
    });
  }

  ngOnInit(): void { }

  onSubmit() {
    console.log(this.addUserForm.value);
    console.log(this.addUserForm.status);
    if (this.addUserForm.valid) {
      const { password, verifyPassword } = this.addUserForm.value;

      if (password !== verifyPassword) {
        alert("Passwords do not match!");
        return;
      }

      this.authService.addUser(this.addUserForm.value).subscribe({
        next: (response) => {
          this.message = response.message;
          this.errorMessage = '';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000); 
        },
        error: (error) => {
          this.errorMessage = 'Please fill out the form correctly.';
        },
        complete: () => {
          console.info('Job fetching complete');
        }
      });
    } else {
      alert("Please fill out the form correctly.");
    }
  }
}
