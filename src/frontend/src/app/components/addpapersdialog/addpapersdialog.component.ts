import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/core/models/dialog-data-model';



@Component({
  selector: 'app-addpapersdialog',
  templateUrl: './addpapersdialog.component.html',
  styleUrls: ['./addpapersdialog.component.css']
})
export class AddpapersdialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddpapersdialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) { }

  ngOnInit(): void {
  }

  confirm() {
    this.data.confirm = true;
    this.dialogRef.close(this.data);
  }

  closedialog() {
    this.data.confirm = false;
    this.dialogRef.close(this.data);
  }
}
