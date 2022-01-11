import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/core/models/dialog-data-model';
import { BibtexService } from 'src/app/core/services/bibtex/bibtex.service';
import { PaperService } from 'src/app/core/services/paper/paper.service';

/**
 * Dialog component to add new Papers.
 * @author Alessandro Soro
 */
@Component({
  selector: 'app-addpapersdialog',
  templateUrl: './addpapersdialog.component.html',
  styleUrls: ['./addpapersdialog.component.css']
})
export class AddpapersdialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddpapersdialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private bibservice: BibtexService,
    private paperservice: PaperService
  ) { }

  ngOnInit(): void {
  }

  /**
   * If Dialog is confirmed, entered data is returned to MypapersComponent
   * and confirm property is set to true.
   */
  confirm() {
    this.data.confirm = true;
    this.dialogRef.close(this.data);
  }

  /**
   * If Dialog is closed, entered data is returned to MypapersComponent
   * and confirm property is set to false.
   */
  closedialog() {
    this.data.confirm = false;
    this.dialogRef.close(this.data);
  }

  /**
   * Triggers after the input for the paper url is entered.
   * Retrieves the bibtex from the added Paper url from the bibtex service.
   */
  onEntered() {
    this.data.id = this.paperservice.paperIdFromURL(this.data.url);
    this.bibservice.getBibtex(this.data.url).subscribe(e => this.data.bibtex = e['bibtex']);
  }
}
