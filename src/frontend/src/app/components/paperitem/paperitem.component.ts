import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';
import { Clipboard } from '@angular/cdk/clipboard';

/**
 * Single Paper item Component for the MypapersComponent.
 * @author Alessandro Soro
 */
@Component({
  selector: 'app-paperitem',
  templateUrl: './paperitem.component.html',
  styleUrls: ['./paperitem.component.css'],
})
export class PaperitemComponent implements OnInit {
  @Input() onePaper: Paper;
  @Input() index: number;
  @Output() deleteRequest = new EventEmitter<number>();
  constructor(
    private paperservice: PaperService,
    private clipboard: Clipboard
  ) { }

  ngOnInit(): void { }

  /**
   * Deletes the Paper from the list and the backend.
   */
  deletePaper() {
    this.paperservice.removeAddedPaper(this.onePaper).subscribe((e) => {
      this.deleteRequest.emit(this.index);
    });
  }

  /**
   * Copies the Bibtex of paper into the clipboard.
   */
  copyBibtex() {
    this.clipboard.copy(this.onePaper.bibtex);
  }
}
