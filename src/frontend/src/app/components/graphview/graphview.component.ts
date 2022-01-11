import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Subject } from 'rxjs';
import { GraphLink } from 'src/app/core/models/graph-links';
import { GraphNode } from 'src/app/core/models/graph-node';
import { Keyword } from 'src/app/core/models/keyword';
import { Paper } from 'src/app/core/models/paper-model';
import * as uuid from 'uuid';

/**
 * Creates the graph that shows a connection between two nodes if they
 * share at least one keyword with each other.
 * 
 * @author Lukas Metzner
 */
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

  /**
   * Calculate graph when component is initialized.
   */
  ngOnInit(): void {
    this.calculateGraph();
  }

  /**
   * Calculate all data and update the graph.
   * Afterwards adjust the zoom of the graph to fit the viewport.
   */
  calculateGraph(): void {
      this.calculateNodes();
      this.calculateLinks();
      this.removeUnlinkedNodes();

      this.update$.next(true);
      this.zoomToFit$.next(true);
      
  }

  /**
   * Remove nodes that have no link to any other node.
   */
  removeUnlinkedNodes(): void {
    let updatedNodes: GraphNode[] = [];
    this.nodes.forEach(n => {
      let keep: boolean = true;
      this.links.forEach(l => {
        if(n.id == l.source || n.id == l.target) {
          keep = false;
        }
      })
      if(!keep) {
        updatedNodes.push(n);
      }
    })
    this.nodes = [...updatedNodes];
  }

  /**
   * Convert papers to nodes object.
   */
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

  /**
   * Convert list of keyword objects to list of strings.
   * @param node Holds the list to convert.
   * @returns String list of keywords.
   */
  extractKeywords(node: GraphNode): string[] {
    let keywords: string[] = [];
    node.keywords.forEach(k => {
      keywords.push(k["keyword"]);
    })
    return keywords
  }

  /**
   * Calculate links between the nodes in the graph.
   * Additionally checks if connection between two nodes already exists.
   */
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

            // Prevent duplicate link between two nodes
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
