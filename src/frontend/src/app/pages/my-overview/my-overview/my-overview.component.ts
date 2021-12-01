import { Component, OnInit } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { likedPaper } from 'src/app/core/models/liked-paper-model';
import { PaperService } from 'src/app/core/services/paper.service';


@Component({
  selector: 'app-my-overview',
  templateUrl: './my-overview.component.html',
  styleUrls: ['./my-overview.component.css']
})
export class MyOverviewComponent implements OnInit {

  likedPapers: likedPaper[] = [
    {
      paperURL: 'test',
      userID: 'test',
      liked: true
    },
    {
      paperURL: 'test22',
      userID: 'test123',
      liked: true
    },
    {
      paperURL: 'test4124',
      userID: 'test124123',
      liked: true
    },
  ];

  constructor(private papersRestService: PaperService) {

  }

  ngOnInit(): void {
    //this.papersRestService.getLikedPapers().subscribe(e => this.likedPapers = e);
  }

  testList(selectedPapers : MatSelectionList ): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(selectedOption => {
      console.log(selectedOption.value);
    });
  }
}
