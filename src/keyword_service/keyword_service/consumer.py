import json
from typing import Any, Dict

from kafka import KafkaConsumer

from keyword_service.keyword_extractor import KeywordExtactor
from keyword_service.models import Paper


class Consumer:
    def __init__(self, topic: str, bootstrap_server: str, group_id: str) -> None:
        self.keyword_extractor: KeywordExtactor = KeywordExtactor()
        self.kafka_consumer: KafkaConsumer = KafkaConsumer(  
            topic,  
            bootstrap_servers = [bootstrap_server],  
            auto_offset_reset = 'earliest',  
            enable_auto_commit = True,  
            group_id = group_id,  
            value_deserializer = lambda x : Paper(**json.loads(x.decode('utf-8'))) 
        )  

    def consume(self):
        for message in self.kafka_consumer:
            item = message.value
            try:
                self._consume_item(item)
            except:
                continue

    def _consume_item(self, item: Paper):
        print(f"Consuming {item.id}")
        keywords = self.keyword_extractor.extact_keywords(item.text)
        print(keywords)
