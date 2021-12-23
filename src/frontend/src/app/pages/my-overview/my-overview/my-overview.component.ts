import { Component, OnInit } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';


@Component({
  selector: 'app-my-overview',
  templateUrl: './my-overview.component.html',
  styleUrls: ['./my-overview.component.css']
})
export class MyOverviewComponent implements OnInit {

  likedPapers: Paper[] = [];

  allLikedPapers: Paper[] = []

  constructor(private papersRestService: PaperService) {

  }

  ngOnInit(): void {
    this.getPapers();
  }

  getPapers(): void {
    this.papersRestService.getPapers().subscribe(e => this.likedPapers = e);
  }

  getLikedPapers(): void {
    this.papersRestService.getlikedPapers().subscribe(e => this.allLikedPapers = e);
  }

  testList(selectedPapers : MatSelectionList ): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(selectedOption => {
      console.log(selectedOption.value);
    });
  }

  like(selectedPapers : MatSelectionList ): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(async selectedOption => {
      await this.papersRestService.addlikedPaper(selectedOption.value);
    });
    this.getLikedPapers();
  }
}
