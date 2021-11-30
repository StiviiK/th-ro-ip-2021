import { Component, OnInit } from '@angular/core';
import { Paper } from 'src/app/core/models/paper-model';

@Component({
  selector: 'app-paperitem',
  templateUrl: './paperitem.component.html',
  styleUrls: ['./paperitem.component.css']
})

export class PaperitemComponent implements OnInit {

  paper: Paper = {} as Paper;

  constructor() { }

  ngOnInit(): void {
    this.paper.author = 'Test1';
    this.paper.title = 'Test2';
  }

}
