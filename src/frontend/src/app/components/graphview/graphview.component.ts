import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Subject } from 'rxjs';
import { GraphLink } from 'src/app/core/models/graph-links';
import { GraphNode } from 'src/app/core/models/graph-node';
import { Keyword } from 'src/app/core/models/keyword';
import { Paper } from 'src/app/core/models/paper-model';
import {MatSliderModule} from '@angular/material/slider';

@Component({
  selector: 'app-graphview',
  templateUrl: './graphview.component.html',
  styleUrls: ['./graphview.component.css']
})
export class GraphviewComponent implements OnInit, OnChanges {
  //@Input() allPapers: Paper[] = [];

  private _allPapers: Paper[] = [];

  @Input()
  set allPapers(papers: Paper[]) {
    this._allPapers = papers;
    this.calculateGraph();
  }

  get allPapers(): Paper[] {
    return this._allPapers;
  }

  links: GraphLink[] = [];
  nodes: GraphNode[] = [];

  update$: Subject<boolean> = new Subject();
  center$: Subject<boolean> = new Subject();
  zoomToFit$: Subject<boolean> = new Subject();

  constructor(
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    this._allPapers = changes.allPapers.currentValue;
  }

  ngOnInit(): void {
    this.calculateGraph();
  }

  calculateGraph(): void {
      this.calculateNodes();
      this.calculateLinks();

      this.center$.next(true);
      this.zoomToFit$.next(true);
      this.update$.next(true);
  }

  calculateNodes(): void {
    this.nodes = [];
    this.allPapers.forEach(p => {
      let node: GraphNode = {} as GraphNode;
      node.id = p.id.replace(".", "");
      node.label = p.title;
      node.keywords = p.keywords;
      this.nodes.push(node);
    })
    this.nodes = [...this.nodes];
  }

  extractKeywords(node: GraphNode): string[] {
    let keywords: string[] = [];
    node.keywords.forEach(k => {
      keywords.push(k["keyword"]);
    })
    return keywords
  }

  calculateLinks(): void {
    this.links = [];
    this.nodes.forEach(n => {
      this.nodes.forEach(nt => {
        if (n.id != nt.id) {
          let keywordsn = this.extractKeywords(n);
          let keywordsnt = this.extractKeywords(nt);
          let common = keywordsn.some(k => keywordsnt.includes(k));
          if (common) {
            let clink: GraphLink = {} as GraphLink;
            clink.id = this.makeLinkId(n.keywords, nt.keywords);
            clink.label = "";
            clink.source = n.id;
            clink.target = nt.id;
            this.links.push(clink);
          }
        }
      })
    })
    this.links = [...this.links];
  }

  makeLinkId(k1: Keyword[], k2: Keyword[]): string {
    let kStr: string[] = [];
    k1.forEach(k => kStr.push(k.keyword));
    k2.forEach(k => kStr.push(k.keyword))
    return kStr.join("");
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
