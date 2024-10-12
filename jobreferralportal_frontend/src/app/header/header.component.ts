
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  username: string = 'John Doe'; // This can be dynamically set later

  constructor() {}

  ngOnInit(): void {
    // Initialization logic here
  }
}
