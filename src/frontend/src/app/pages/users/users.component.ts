import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatSelectionList } from '@angular/material/list';
import { LikedPaper } from 'src/app/core/models/liked-paper-model';
import { Paper } from 'src/app/core/models/paper-model';

interface UserResponse {
  id: string;
  username: string;
  papers: Paper[];
  likedPapers: LikedPaper[];
}

/**
 * Displays the list of users and their papers.
 * @author Stefan Kürzeder
 */
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users$: Observable<UserResponse[]>;

  selectedUser: UserResponse;

  constructor(private http: HttpClient, private config: ConfigService) {}

  /**
   * Retrieves all users from the database.
   */
  ngOnInit(): void {
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
   * Converter for the Object of keywords into a string
   * so it can be displayed in the tooltip.
   * @param keywords Keywords
   * @returns String of all keywords
   */
  convertKeywordsTooltip(keywords: any): string {
    return Object.keys(keywords).reduce((sum, key) => {
      sum.push(`${key}: ${keywords[key].name}`);
      return sum;
    }, []).join('\n');
  }
}
