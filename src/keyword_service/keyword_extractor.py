import yake

class KeywordExtractor:
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

    def extract_keywords(self, text: str):
        return self.custom_kw_extractor.extract_keywords(text)