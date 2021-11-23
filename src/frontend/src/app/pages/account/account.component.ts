import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  response: Observable<any>;
  error: string;

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.response = this.httpClient.get<{ message: string }>('http://localhost:8080/hello')
    .pipe(catchError(err => {
      this.error = err;
      return err;
    }));
  }

}
