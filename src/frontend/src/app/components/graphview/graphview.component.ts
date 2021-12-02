import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { GraphLink } from 'src/app/core/models/graph-links';
import { GraphNode } from 'src/app/core/models/graph-node';
import { Paper } from 'src/app/core/models/paper-model';
import { PaperService } from 'src/app/core/services/paper/paper.service';

@Component({
  selector: 'app-graphview',
  templateUrl: './graphview.component.html',
  styleUrls: ['./graphview.component.css']
})
export class GraphviewComponent implements OnInit {

  papers: Paper[] = [];

  links: GraphLink[] = [];
  nodes: GraphNode[] = [];

  update$: Subject<boolean> = new Subject();
  center$: Subject<boolean> = new Subject();
  zoomToFit$: Subject<boolean> = new Subject();

  constructor(
    private papersRestService: PaperService
  ) { }

  ngOnInit(): void {
    this.papersRestService.getPapers().subscribe(e => {
      this.papers = e;
      this.calculateNodes();
      this.calculateLinks();
      
      this.center$.next(true);
      this.zoomToFit$.next(true);
      this.update$.next(true);
    });
  }

  calculateNodes(): void {
    this.papers.forEach(p => {
      let node: GraphNode = {} as GraphNode;
      node.id = p.id.replace(".", "");
      node.label = p.title;
      node.keywords = p.keywords;
      this.nodes.push(node);
    })
  }

  extractKeywords(node: GraphNode): string[] {
    let keywords: string[] = [];
    node.keywords.forEach(k => {
      keywords.push(k["keyword"]);
    })
    return keywords
  }

  calculateLinks(): void {
    let count: number = 0;
    this.nodes.forEach(n => {
      this.nodes.forEach(nt => {
        if (n.id != nt.id) {
          let keywordsn = this.extractKeywords(n);
          let keywordsnt = this.extractKeywords(nt);
          let common = keywordsn.some(k => keywordsnt.includes(k));
          if (common) {
            let clink: GraphLink = {} as GraphLink;
            clink.id = count;
            clink.label = "";
            clink.source = n.id;
            clink.target = nt.id;
            this.links.push(clink);
            count++;
          }
        }
      })
    })
  }

  makeString(): string {
    let outString: string = '';
    let inOptions: string = 'abcdefghijklmnopqrstuvwxyz0123456789';

    for (let i = 0; i < 32; i++) {

      outString += inOptions.charAt(Math.floor(Math.random() * inOptions.length));

    }

    return outString;
  }

  result: string = this.makeString();

}
