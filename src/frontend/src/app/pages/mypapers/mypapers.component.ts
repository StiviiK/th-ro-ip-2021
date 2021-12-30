import { Component, OnInit } from '@angular/core';
import { AddpapersdialogComponent } from 'src/app/components/addpapersdialog/addpapersdialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaperService } from 'src/app/core/services/paper/paper.service';
import { Paper } from 'src/app/core/models/paper-model';
import { ElementRef, ViewChild, AfterViewInit} from '@angular/core';



@Component({
  selector: 'app-mypapers',
  templateUrl: './mypapers.component.html',
  styleUrls: ['./mypapers.component.css'],
})
export class MypapersComponent implements OnInit {

  @ViewChild('graphview') graphview: ElementRef;
  
  currentPapers: Paper[] = [];
  allPapers: Paper[] = [];
  query: string = "";

  constructor(
    public matDialog: MatDialog,
    private papersRestService: PaperService
  ) {}

  ngOnInit(): void {
    this.getPapers();
  }

  filterList(): void {
    console.log(this.query);
    this.currentPapers = [];
    this.allPapers.forEach(p => {
      if (p.title.toLowerCase().includes(this.query.toLowerCase())) {
        this.currentPapers.push(p);
      }
    })
  }

  getPapers(): void {
    this.papersRestService.getPapers().subscribe(e => this.allPapers = e);
  }

  openNewPapersDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = 'new-paper-component';
    dialogConfig.height = '500px';
    dialogConfig.width = '800px';
    dialogConfig.data = {
      confirm: false,
    };

    const newPapersDialog = this.matDialog.open(
      AddpapersdialogComponent,
      dialogConfig
    );

    newPapersDialog.afterClosed().subscribe((data) => {
      if (data.confirm) {
        this.papersRestService.addPaper(data).subscribe((e) => {
          this.allPapers.push(e);
          this.ngOnInit();
          console.log(this.allPapers);
        });
      }
    });
  }
}
