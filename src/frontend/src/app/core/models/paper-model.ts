import { Keyword } from './keyword';

export interface Paper {
  id: string;
  url: string;
  keywords: Keyword[];
  authors: Author[];
  title: string;
  bibtex: string;
}

interface Author {
  name: string;
}
