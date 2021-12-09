import { Keyword } from "./keyword";

export interface Paper {
    id: string;
    url: string;
    keywords: Keyword[];
    author: string;
    title: string;
  }
