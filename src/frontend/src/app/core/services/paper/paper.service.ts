import { Injectable } from '@angular/core';
import { DialogData } from '../../models/dialog-data-model';
import {HttpClient} from '@angular/common/http';
import { Paper } from '../../models/paper-model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../config.service';

@Injectable({
  providedIn: 'root'
})
export class PaperService {

  public item: Paper | undefined;

  constructor(private http: HttpClient, private config: ConfigService) { }

  public getPapers(): Observable<Paper[]> {
    return this.http.get<Paper[]>(`${this.config.getConfig('api_endpoint')}/papers/`);
  }

  public addPaper(paperToAdd: DialogData): Observable<Paper> {
    console.log(paperToAdd);
    return this.http.put<Paper>(`${this.config.getConfig('api_endpoint')}/papers/`, paperToAdd);
  }

  public addLikedPaper(paperToLike: any): Observable<Paper> {
    return this.http.put<Paper>(`${this.config.getConfig('api_endpoint')}/self/addLikedPaper`, paperToLike);
  }

  public removeLikedPaper(paperToRemove: any): void {
    this.http.put<Paper>(`${this.config.getConfig('api_endpoint')}/self/removeLikedPaper`, paperToRemove).subscribe();
  }

  public async getlikedPapers(): Promise<Observable<Paper[]>> {
    return this.http.get<Paper[]>(`${this.config.getConfig('api_endpoint')}/self/getLikedPapers`);
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
