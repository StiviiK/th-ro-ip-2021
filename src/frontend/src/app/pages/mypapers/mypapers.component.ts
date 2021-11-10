import { Component, OnInit } from '@angular/core';
import { AddpapersdialogComponent } from 'src/app/components/addpapersdialog/addpapersdialog.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-mypapers',
  templateUrl: './mypapers.component.html',
  styleUrls: ['./mypapers.component.css']
})
export class MypapersComponent implements OnInit {

  constructor(public matDialog: MatDialog) { }
  

  ngOnInit(): void {
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
          console.log(data)
        }
      }
    )
  }
}
