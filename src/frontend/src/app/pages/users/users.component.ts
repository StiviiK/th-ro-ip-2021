import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/core/services/authentication-service.service';
import { ConfigService } from 'src/app/core/services/config.service';
import { MatSelectionList } from '@angular/material/list';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {

  users$: Observable<User[]>;
  userSelected: boolean = false;

  constructor(private http: HttpClient, private config: ConfigService) { }

  ngOnInit(): void {
    this.users$ = this.http.get<User[]>(`${this.config.getConfig('api_endpoint')}/users`);
  }

  delete(selectedUser: MatSelectionList): void {
    selectedUser.selectedOptions.selected.forEach(
      (ele) => {
        var user = ele.value as User;
        this.http.post(`${this.config.getConfig('api_endpoint')}/users/delete`, user)
          .subscribe(
            () => {
              this.users$ = this.http.get<User[]>(`${this.config.getConfig('api_endpoint')}/users`);
            },
          );
      },
    );
  }

  changedList(selectedUser: MatSelectionList): void {
    if (selectedUser.selectedOptions.selected.length > 0) {
      this.userSelected = true;
    } else {
      this.userSelected = false;
    }
  }

}
