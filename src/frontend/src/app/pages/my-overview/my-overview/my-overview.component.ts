import { Component, OnInit } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { forkJoin } from 'rxjs';
import { Paper } from 'src/app/core/models/paper-model';
import { BibtexService } from 'src/app/core/services/bibtex/bibtex.service';
import { PaperService } from 'src/app/core/services/paper/paper.service';

@Component({
  selector: 'app-my-overview',
  templateUrl: './my-overview.component.html',
  styleUrls: ['./my-overview.component.css']
})
export class MyOverviewComponent implements OnInit {

  allPapers: Paper[] = [];
  paperSelected: boolean = false;
  allLikedPapers: Paper[] = []
  likedPaperSelected: boolean = false;
  bibtex: string = "";

  constructor(
    private papersRestService: PaperService,
    private bibservice: BibtexService) {
  }

  ngOnInit(): void {
    this.setUp();
  }

  setUp(): void {
    this.getLikedPapers();
    this.getPapers();
  }

  async getPapers(): Promise<void> {
    await this.papersRestService.getPapers()
      .then(response => {
        response.subscribe(e => {
          this.allPapers = e;
          if (this.allLikedPapers) {
            this.allLikedPapers.forEach(likedpaper => {
              this.allPapers.forEach((paper, index = 0) => {
                if (paper.id === likedpaper.id) {
                  this.allPapers.splice(index, 1);
                }
                index++;
              })
            })
          }
        });
      })
  }

  async getLikedPapers(): Promise<void> {
    await this.papersRestService.getlikedPapers()
      .then(response => {
        response.subscribe(e => this.allLikedPapers = e);
      })
  }

  // bibtexExport(selectedPapers: MatSelectionList, selectedLikedPapers: MatSelectionList): void {
  //   if (selectedPapers) {
  //     let selectedPaperOptions = selectedPapers.selectedOptions.selected;
  //     selectedPaperOptions.forEach(selectedOption => {
  //       this.bibservice.getBibtex(selectedOption.value.url).subscribe(e => {
  //         this.bibtex = this.bibtex + e.bibtex + "\n";
  //       });
  //     });
  //   }
  //   if (selectedLikedPapers) {
  //     let selectedLikedPaperOptions = selectedLikedPapers.selectedOptions.selected;
  //     selectedLikedPaperOptions.forEach(selectedOption => {
  //       this.bibservice.getBibtex(selectedOption.value.url).subscribe(e => {
  //         this.bibtex = this.bibtex + e.bibtex + "\n";
  //       });
  //     });
  //   }
  //   const blob = new Blob([this.bibtex], {type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"})
  //   const url = window.URL.createObjectURL(blob);
  //   window.open(url);
  //   this.bibtex = "";
  // }

  like(selectedPapers: MatSelectionList): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(async selectedOption => {
      this.papersRestService.addLikedPaper(selectedOption.value).subscribe(e => {
        this.allLikedPapers.push(e);
        this.allPapers.forEach((paper, index = 0) => {
          if (paper.id === e.id) {
            this.allPapers.splice(index, 1);
          }
          index++;
        })
      })
    });
    this.paperSelected = false;
  }

  removeLike(selectedPapers: MatSelectionList): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(async selectedOption => {
      this.papersRestService.removeLikedPaper(selectedOption.value);
      this.allPapers.push(selectedOption.value);
      this.allLikedPapers.forEach((paper, index = 0) => {
        if (paper.id === selectedOption.value.id) {
          this.allLikedPapers.splice(index, 1);
        }
        index++;
      })
    });
    this.likedPaperSelected = false;
  }

  changedList(selectedPapers: MatSelectionList): void {
    if (selectedPapers.selectedOptions.selected.length > 0) {
      this.paperSelected = true;
    } else {
      this.paperSelected = false;
    }
  }

  changedLikedList(selectedPapers: MatSelectionList): void {
    if (selectedPapers.selectedOptions.selected.length > 0) {
      this.likedPaperSelected = true;
    } else {
      this.likedPaperSelected = false;
    }
  }

}
