import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BibtexService {
  private baseUrl: string = 'https://arxiv.org/bibtex/';

  constructor(private http: HttpClient) { 
  }

  getBibtex(url: string): string {
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
    return 'Bibtex';
    // return this.http.get(url);
  }
}
