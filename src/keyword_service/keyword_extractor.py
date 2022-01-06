#!/usr/bin/env python

"""Contains the logic for extracting the keywords."""
__author__ = "Lukas Metzner"

from typing import List
import yake

class KeywordExtractor:
    """ KeywordExtractor class

    Args:
        custom_kw_extractor (KeywordExtractor): Yake keyword extractor class.
    """
    def __init__(self) -> None:
        language = "en"
        max_ngram_size = 3
        deduplication_thresold = 0.9
        deduplication_algo = 'seqm'
        windowSize = 1
        numOfKeywords = 20

        self.custom_kw_extractor = yake.KeywordExtractor(
            lan=language, 
            n=max_ngram_size, 
            dedupLim=deduplication_thresold, 
            dedupFunc=deduplication_algo, 
            windowsSize=windowSize, 
            top=numOfKeywords, 
            features=None
        )

    def extract_keywords(self, text: str) -> List[str]:
        """Extract keyword from text.

        Args:
            text (str): Input text.

        Returns:
            List[str]: Keywords.
        """
        return self.custom_kw_extractor.extract_keywords(text)