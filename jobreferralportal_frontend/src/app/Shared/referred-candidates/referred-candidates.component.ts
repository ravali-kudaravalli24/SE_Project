import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-referred-candidates',
  templateUrl: './referred-candidates.component.html',
  styleUrls: ['./referred-candidates.component.css'],
  standalone:true,
  imports:[CommonModule]
})

export class ReferredCandidatesComponent implements OnInit {
  candidates: any[] = [];
  

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    if (history.state && history.state.data) {
      this.candidates = history.state.data;
      console.log('Candidates data from state:', this.candidates);
    } else {
      console.error('No data found in the router state.');
    }
  }
}

  
