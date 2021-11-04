from pydantic import BaseModel


class Paper(BaseModel):
    id: str
    url: str
    title: str
    author: str
    text: str
    bibtex: str