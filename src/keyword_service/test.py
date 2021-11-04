from time import sleep

from test_producer.test_producer import TestProducer


def main():
    test_producer = TestProducer("localhost:29092", "test_topic")
    while True:
        test_producer.produce_item()
        sleep(2)


if __name__ == "__main__":
    main()
