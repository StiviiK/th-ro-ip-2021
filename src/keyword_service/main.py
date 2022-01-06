#!/usr/bin/env python

"""Contains the REST backend for the keyword service."""
__author__ = "Lukas Metzner"

from typing import Dict, List
from flask import Flask, request
from keyword_extractor import KeywordExtractor

app = Flask(__name__)
ke = KeywordExtractor()

@app.route("/")
def root() -> str:
    """ Root endpoint.

    Returns:
        str: Short doc message.
    """
    return "Use /extract and provide a text using the parameter: text."

@app.route("/extract")
def keywords() -> Dict[str, List[str]]:
    """ Extact keywords from input text.

    Returns:
        Dict[str, List[str]]: JSON object with keyword list.
    """
    text = request.args.get('text')
    if not text:
        return "Please provide a text using the parameter 'text'."
    keywords = ke.extract_keywords(text)
    return {
        "keywords": keywords
    }

if __name__ == "__main__":
    # Start Backend
    app.run(host="0.0.0.0", port=5002)