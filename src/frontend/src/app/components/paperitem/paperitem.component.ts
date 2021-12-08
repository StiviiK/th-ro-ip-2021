import { Component, Inject, Input, OnInit } from '@angular/core';
import { Paper } from 'src/app/core/models/paper-model';

@Component({
  selector: 'app-paperitem',
  templateUrl: './paperitem.component.html',
  styleUrls: ['./paperitem.component.css']
})

export class PaperitemComponent implements OnInit {

  @Input() onePaper!: Paper;
  constructor() { }

  ngOnInit(): void {
  }

}
