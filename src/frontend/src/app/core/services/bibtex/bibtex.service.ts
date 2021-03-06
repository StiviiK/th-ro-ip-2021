import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bibtex } from '../../models/bibtex.model';
import { ConfigService } from '../config.service';

@Injectable({
  providedIn: 'root',
})
export class BibtexService {
  private baseUrl: string = `${this.config.getConfig('api_endpoint')}/bibtex/`;

  constructor(private http: HttpClient, private config: ConfigService) { }

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
