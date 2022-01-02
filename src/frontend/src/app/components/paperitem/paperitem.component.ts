import { Component, Input, OnInit } from '@angular/core';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';

@Component({
  selector: 'app-paperitem',
  templateUrl: './paperitem.component.html',
  styleUrls: ['./paperitem.component.css']
})

export class PaperitemComponent implements OnInit {

  @Input() onePaper: Paper;
  constructor(private paperservice: PaperService) { }

  ngOnInit(): void {
  }

  deletePaper() {
    this.paperservice.deletePaper(this.onePaper.id).subscribe(e => {
      alert(e);
    })
  }

}
