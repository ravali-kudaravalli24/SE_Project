import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../Services/services/auth.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { HrService } from '../../Services/services/hr.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-referred-candidates',
  standalone: true,
  templateUrl: './referred-candidates.component.html',
  styleUrls: ['./referred-candidates.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ReferredCandidatesComponent implements OnInit {
  candidates: any[] = [];

  

  constructor(private router: Router, private route: ActivatedRoute,private authService:AuthService,private hrService:HrService,private employeeService:EmployeeService) {}
  userRole: string | null = null;
  searchQuery: string = '';
  statusOptions: string[] = [
    'UNDER CONSIDERATION',
    'ONLINE ASSESSMENT',
    'INTERVIEW ROUND 1',
    'INTERVIEW ROUND 2',
    'MANAGERIAL ROUND',
    'ACCEPTED',
    'REJECTED'
  ];
  

  ngOnInit(): void {
    this.userRole = this.authService.getUserRole();
    
    if (history.state && history.state.data) {
      this.candidates = history.state.data;
      console.log('Candidates data from state:', this.candidates);
    }  
      
    
  }
}

  