import { Component, OnInit, SimpleChanges } from '@angular/core';
import { AddpapersdialogComponent } from 'src/app/components/addpapersdialog/addpapersdialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaperService } from 'src/app/core/services/paper/paper.service';
import { Paper } from 'src/app/core/models/paper-model';


/**
 * Displays the papers of a user in a list next to the graph.
 * @author Lukas Metzner
 * @author Alessandro Sorro
 */
@Component({
  selector: 'app-mypapers',
  templateUrl: './mypapers.component.html',
  styleUrls: ['./mypapers.component.css'],
})
export class MypapersComponent implements OnInit {

  currentPapers: Paper[] = [];
  allPapers: Paper[] = [];
  query: string = "";

  constructor(
    public matDialog: MatDialog,
    private papersRestService: PaperService
  ) {}

  /**
   * Retrieve all papers of an user at initialization time.
   */
  ngOnInit(): void {
    this.getPapers();
  }

  /**
   * Make full text search on all titles of the paper and adjust current list.
   */
  deletePaper(index: number): void {
    this.currentPapers.splice(index, 1);
    this.ngOnInit();
  }

  filterList(): void {
    this.currentPapers = [];
    this.allPapers.forEach(p => {
      if (p.title.toLowerCase().includes(this.query.toLowerCase())) {
        this.currentPapers.push(p);
      }
    })
  }

  getPapers(): void {
    this.papersRestService.getAddedPapers().subscribe(e => {
      this.allPapers = e;
      this.currentPapers = e;
    });
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
        });
      }
    });
  }
}
