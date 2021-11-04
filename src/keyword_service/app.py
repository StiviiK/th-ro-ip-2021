from keyword_service.consumer import Consumer


def main():
    consumer = Consumer("test_topic", "localhost:29092", "group")
    consumer.consume()

if __name__ == "__main__":
    main()