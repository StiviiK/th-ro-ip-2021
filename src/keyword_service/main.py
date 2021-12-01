from flask import Flask, request
from keyword_extractor import KeywordExtractor

app = Flask(__name__)
ke = KeywordExtractor()

@app.route("/")
def root():
    return "Use /extract and provide a text using the parameters."

@app.route("/extract")
def keywords():
    text = request.args.get('text')
    if not text:
        return "Please provide a text using the parameter 'text'."
    keywords = ke.extract_keywords(text)
    return {
        "keywords": keywords
    }

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5002)