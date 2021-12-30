import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Bibtex } from '../../models/bibtex.model';

@Injectable({
  providedIn: 'root'
})
export class BibtexService {
  private baseUrl: string = `${environment.apiUrl}/bibtex/`;

  constructor(private http: HttpClient) {
  }

  getBibtex(url: string): Observable<Bibtex> {
    let id: string;
    if (!url.includes('arxiv')) {
      throw new Error('Only arxiv is supported');
    }
    let splitted: string[] = url.split('/');
    id = splitted[splitted.length - 1];

    if (url.includes('pdf')) {
      id = id.substring(0, splitted[splitted.length - 1].length - 4);
    }

    url = this.baseUrl + id;

    return this.http.get<Bibtex>(url);
  }
}
