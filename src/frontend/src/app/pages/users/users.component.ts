import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatSelectionList } from '@angular/material/list';
import { LikedPaper } from 'src/app/core/models/liked-paper-model';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';

interface UserResponse {
  id: string;
  username: string;
  papers: Paper[];
  likedPapers: LikedPaper[];
}

/**
 * Displays the list of users and their papers.
 * @author Stefan Kürzeder
 * @author Alessandro Soro
 */
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users$: Observable<UserResponse[]>;
  selectedUser: UserResponse;
  paperSelected: boolean = false;

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private papersRestService: PaperService
  ) {}

  ngOnInit(): void {
    this.getUsers();
  }

  /**
   * Retrieves all users from the database.
   */
  getUsers(): void {
    this.users$ = this.http.get<UserResponse[]>(
      `${this.config.getConfig('api_endpoint')}/users`
    );
  }

  /**
   * Specifies the user that is selected.
   * @param selectedUser Selected user
   */
  changedList(selectedUser: MatSelectionList): void {
    if (selectedUser.selectedOptions.selected.length > 0) {
      this.selectedUser = selectedUser.selectedOptions.selected[0].value;
    } else {
      this.selectedUser = null;
    }
  }

  /**
   * Selected papers will be added to the current Users added Paper.
   * After being Added to the user the paper is receiving a Like.
   * User are being retrieved afterwards and Updated.
   * @param selectedPapers All selected papers
   */
  like(selectedPapers: MatSelectionList): void {
    let selectedOptions = selectedPapers.selectedOptions.selected;
    selectedOptions.forEach(async (selectedOption) => {
      this.papersRestService
        .addPaper(selectedOption.value)
        .subscribe((addedPaper) => {
          this.papersRestService.addLikedPaper(addedPaper).subscribe((e) => {
            this.getUsers();
            this.paperSelected = false;
            this.selectedUser = null;
          });
        });
    });
  }

  /**
   * Sets paperSelected property to true
   * if at least one paper is selected.
   * @param selectedPapers All selected papers
   */
  changedListPapers(selectedPapers: MatSelectionList): void {
    if (selectedPapers.selectedOptions.selected.length > 0) {
      this.paperSelected = true;
    } else {
      this.paperSelected = false;
    }
  }

  /**
   * Converter for the Object of keywords into a string
   * so it can be displayed in the tooltip.
   * @param keywords Keywords
   * @returns String of all keywords
   */
  convertKeywordsTooltip(keywords: any): string {
    return Object.keys(keywords)
      .reduce((sum, key) => {
        sum.push(`${key}: ${keywords[key].name}`);
        return sum;
      }, [])
      .join('\n');
  }
}
