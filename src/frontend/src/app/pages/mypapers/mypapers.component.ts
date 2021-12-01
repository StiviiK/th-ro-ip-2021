import { Component, OnInit } from '@angular/core';
import { AddpapersdialogComponent } from 'src/app/components/addpapersdialog/addpapersdialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaperService } from 'src/app/core/services/paper.service';
import { Paper } from 'src/app/core/models/paper-model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-mypapers',
  templateUrl: './mypapers.component.html',
  styleUrls: ['./mypapers.component.css']
})
export class MypapersComponent implements OnInit {


  papers: Paper[] = [];

  constructor(public matDialog: MatDialog, private papersRestService: PaperService) { }

  ngOnInit(): void {
    this.getPapers();
  }

  getPapers(): void {
    console.log('Getting Papers')
    this.papersRestService.getPapers().subscribe(e => this.papers = e);
  }

  openNewPapersDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = "new-paper-component";
    dialogConfig.height = "500px";
    dialogConfig.width = "800px";
    dialogConfig.data = {
      paperURL: '',
      genKeywords: false,
      userKeywords: '',
      bibtex: '',
      confirm: false,
    }

    const newPapersDialog = this.matDialog.open(AddpapersdialogComponent, dialogConfig);

    newPapersDialog.afterClosed().subscribe(
      data => {
        if (data.confirm) {
          this.papersRestService.addPaper(data)
        }
      }
    )
  }
}
