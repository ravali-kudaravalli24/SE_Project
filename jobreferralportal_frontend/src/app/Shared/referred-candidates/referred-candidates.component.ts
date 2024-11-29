import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../Services/services/auth.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { HrService } from '../../Services/services/hr.service';
@Component({
  selector: 'app-referred-candidates',
  templateUrl: './referred-candidates.component.html',
  styleUrls: ['./referred-candidates.component.css'],
  standalone:true,
  imports:[CommonModule]
})

export class ReferredCandidatesComponent implements OnInit {
  candidates: any[] = [];
  

  constructor(private router: Router, private route: ActivatedRoute,private authService:AuthService,private hrService:HrService,private employeeService:EmployeeService) {}

  ngOnInit(): void {
    if (history.state && history.state.data) {
      this.candidates = history.state.data;
      console.log('Candidates data from state:', this.candidates);
    }  
      
    
  }
}

  