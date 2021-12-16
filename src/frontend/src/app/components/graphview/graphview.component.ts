import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Subject } from 'rxjs';
import { GraphLink } from 'src/app/core/models/graph-links';
import { GraphNode } from 'src/app/core/models/graph-node';
import { Keyword } from 'src/app/core/models/keyword';
import { Paper } from 'src/app/core/models/paper-model';
import * as uuid from 'uuid';

@Component({
  selector: 'app-graphview',
  templateUrl: './graphview.component.html',
  styleUrls: ['./graphview.component.css']
})
export class GraphviewComponent implements OnInit, OnChanges {

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
            clink.id = "\\3" + uuid.v4() + " ";
            clink.label = "";
            clink.source = n.id;
            clink.target = nt.id;

            let add = true;
            this.links.forEach(l => {
              if (l.source == clink.source && l.target == clink.target) {
                add = false;
              }
              if (l.source == clink.target && l.target == clink.source) {
                add = false;
              }
            })
            if (add) {
              this.links.push(clink);
            }
          }
        }
      })
    })
    this.links = [...this.links];
  }
}
