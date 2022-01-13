import { Component, OnInit } from '@angular/core';
import { MatSelectionList } from '@angular/material/list';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';

/**
 * Displays the added papers and the liked papers of a user in a list.
 * @author Alessandro Soro
 */
@Component({
  selector: 'app-my-overview',
  templateUrl: './my-overview.component.html',
  styleUrls: ['./my-overview.component.css'],
})
export class MyOverviewComponent implements OnInit {
  allPapers: Paper[] = [];
  paperSelected: boolean = false;
  allLikedPapers: Paper[] = [];
  likedPaperSelected: boolean = false;
  bibtex: string = '';

  constructor(private papersRestService: PaperService) {}

  /**
   * Retrieves all papers of an user at initialization time.
   */
  ngOnInit(): void {
    this.getPapers();
  }

  /**
   * Retrieves all papers of an user.
   * Retrieves all liked papers of an user.
   * After retrieveing both lists removes duplicates
   * from the list of papers from a user.
   */
  async getPapers(): Promise<void> {
    await this.papersRestService.getlikedPapers().then((response) => {
      response.subscribe((e) => (this.allLikedPapers = e));
      this.papersRestService.getAddedPapers().subscribe((e) => {
        this.allPapers = e;
        if (this.allLikedPapers) {
          this.allLikedPapers.forEach((likedpaper) => {
            this.allPapers.forEach((paper, index = 0) => {
              if (paper.id === likedpaper.id) {
                this.allPapers.splice(index, 1);
              }
              index++;
            });
          });
        }
      });
    });
  }

  /**
   * Gets from every selected paper or liked paper their bibtex
   * and export that into a new window.
   * In the new window there will be a text with every bibtex
   * from the selected papers.
   * @param selectedPapers All selected papers
   * @param selectedLikedPapers All selected liked papers.
   */
  bibtexExport(
    selectedPapers: MatSelectionList,
    selectedLikedPapers: MatSelectionList
  ): void {
    if (selectedPapers) {
      let selectedPaperOptions = selectedPapers.selectedOptions.selected;
      selectedPaperOptions.forEach((selectedOption) => {
        this.bibtex = this.bibtex + selectedOption.value.bibtex + '\n\n';
      });
    }
    if (selectedLikedPapers) {
      let selectedLikedPaperOptions =
        selectedLikedPapers.selectedOptions.selected;
      selectedLikedPaperOptions.forEach((selectedOption) => {
        this.bibtex = this.bibtex + selectedOption.value.bibtex + '\n\n';
      });
    }
    const blob = new Blob([this.bibtex], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    window.open(url);
    this.bibtex = '';
  }

  /**
   * Selected papers will be added to the list of liked papers
   * of the user and removed from the list of added papers.
   * @param selectedPapers All selected papers
   */
  like(selectedPapers: MatSelectionList): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(async (selectedOption) => {
      this.papersRestService
        .addLikedPaper(selectedOption.value)
        .subscribe((e) => {
          this.allLikedPapers.push(e);
          this.allPapers.forEach((paper, index = 0) => {
            if (paper.id === e.id) {
              this.allPapers.splice(index, 1);
            }
            index++;
          });
        });
    });
    this.paperSelected = false;
  }

  /**
   * Selected liked papers will be removed from the liked list of papers
   * and added to the list of all papers.
   * @param selectedLikedPapers All selected liked papers
   */
  removeLike(selectedLikedPapers: MatSelectionList): void {
    let selectedOptions = selectedLikedPapers.selectedOptions.selected;
    selectedOptions.forEach(async (selectedOption) => {
      this.papersRestService.removeLikedPaper(selectedOption.value);
      this.allPapers.push(selectedOption.value);
      this.allLikedPapers.forEach((paper, index = 0) => {
        if (paper.id === selectedOption.value.id) {
          this.allLikedPapers.splice(index, 1);
        }
        index++;
      });
    });
    this.likedPaperSelected = false;
  }

  /**
   * Sets paperSelected property to true
   * if at least one paper is selected.
   * @param selectedPapers All selected papers
   */
  changedList(selectedPapers: MatSelectionList): void {
    if (selectedPapers.selectedOptions.selected.length > 0) {
      this.paperSelected = true;
    } else {
      this.paperSelected = false;
    }
  }

  /**
   * Sets likedPaperSelected property to true
   * if at least one liked paper is selected.
   * @param selectedPapers All selected liked papers
   */
  changedLikedList(selectedLikedPapers: MatSelectionList): void {
    if (selectedLikedPapers.selectedOptions.selected.length > 0) {
      this.likedPaperSelected = true;
    } else {
      this.likedPaperSelected = false;
    }
  }

  /**
   * Converter for the Object of keywords into a string
   * so it can be displayed in the tooltip.
   * @param keywords Keywords
   * @returns String of all keywords
   */
  convertKeywordsTooltip(keywords: any): String {
    const results = [];
    Object.keys(keywords).reduce((sum, key) => {
      sum.push(`${key}: ${keywords[key].name}`);
      return sum;
    }, results);
    return results.join('\n');
  }
}
