import { Component, Input, OnInit } from '@angular/core';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';
import { Clipboard } from '@angular/cdk/clipboard';

@Component({
  selector: 'app-paperitem',
  templateUrl: './paperitem.component.html',
  styleUrls: ['./paperitem.component.css']
})

export class PaperitemComponent implements OnInit {

  @Input() onePaper: Paper;
  constructor(
    private paperservice: PaperService,
    private clipboard: Clipboard
  ) { }

  ngOnInit(): void {
  }

  deletePaper() {
    this.paperservice.removeAddedPaper(this.onePaper).subscribe(e => {
      this.onePaper = null;
    })
  }

  copyBibtex(){
    this.clipboard.copy(this.onePaper.bibtex);
  }

}
