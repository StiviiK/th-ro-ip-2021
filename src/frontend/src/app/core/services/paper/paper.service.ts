import { Injectable } from '@angular/core';
import { DialogData } from '../../models/dialog-data-model';
import {HttpClient} from '@angular/common/http';
import { Paper } from '../../models/paper-model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaperService {

  public item: Paper | undefined;

  constructor(private http: HttpClient) { }

  public getPapers(): Observable<Paper[]> {
    return this.http.get<Paper[]>(`${environment.apiUrl}/papers`);
  }

  public addPaper(paperToAdd: DialogData): void {
    console.log(paperToAdd);
    this.http.put<DialogData>(`${environment.apiUrl}/papers`, paperToAdd).subscribe();
  }

  public paperIdFromURL(url: string): string {
    let id: string;
    if (!url.includes('arxiv')) {
      throw new Error('Only arxiv is supported');
    }
    let splitted: string[] = url.split('/');
    id = splitted[splitted.length - 1];

    if (url.includes('pdf')) {
      id = id.substring(0, splitted[splitted.length - 1].length - 4);
    }
    return id;
  }
}
