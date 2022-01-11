import { Keyword } from './keyword';

export interface GraphNode {
  id: string,
  label: string,
  keywords: Keyword[],
}