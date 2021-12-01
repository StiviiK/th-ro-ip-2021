import { Injectable } from '@angular/core';
import { DialogData } from '../models/dialog-data-model';
import {HttpClient} from '@angular/common/http';
import { Paper } from '../models/paper-model';
import { Observable } from 'rxjs';
import {tap} from 'rxjs/operators';
import { likedPaper } from '../models/liked-paper-model';

@Injectable({
  providedIn: 'root'
})
export class PaperService {

  public item: Paper | undefined;

  constructor(private http: HttpClient) { }

  public getPapers(): Observable<Paper[]> {
    return this.http.get<Paper[]>('papers')
  }

  public addPaper(paperToAdd: DialogData): void {
    console.log(paperToAdd);
    this.http.put<DialogData>('papers/addPapers/' + paperToAdd, '').subscribe();
  }

  public getLikedPapers(): Observable<likedPaper[]> {
    return this.http.get<likedPaper[]>('likedPapers')
  }
}
