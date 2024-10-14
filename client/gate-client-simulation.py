import threading

import pika
import json
import time
import random

# RabbitMQ connection parameters
credentials = pika.PlainCredentials('admin', 'cas735')
connection_params = pika.ConnectionParameters(
    host='localhost',  # If running on the same machine as Docker
    port=5672,  # Default AMQP port, not the management interface port
    credentials=credentials
)

# Connect to RabbitMQ
connection = pika.BlockingConnection(connection_params)
channel = connection.channel()

# Declare the command queue as durable
channel.queue_declare(queue='gate.command.queue', durable=True)
channel.exchange_declare(exchange='gate.command', exchange_type='direct', durable=True)
channel.queue_bind(exchange='gate.command', queue='gate.command.queue', routing_key='command')

# Declare the access request queue as durable
channel.queue_declare(queue='access.request.queue', durable=True)
channel.exchange_declare(exchange='access', exchange_type='direct', durable=True)
channel.queue_bind(exchange='access', queue='access.request.queue', routing_key='request')

def basic_publish(body):
    print(f"{body}")


def send_message(transponderId, gate, lot, timestamp):
    # Prepare the access request message
    access_request = {
        "action": "ENTER_GATE_TRANSPONDER",
        "payload": {
            "transponderId": transponderId,
            "gate": gate,
            "lot": lot,
            "timestamp": timestamp
        }
    }

    channel.basic_publish(
        exchange='access',
        routing_key='request',
        body=json.dumps(access_request))
    basic_publish(body=json.dumps(access_request))


def simulate_requests():
    transponderIds = ["T_001", "T_002"]
    gates = ["E1", "E2"]
    lot = "A"

    for index, transponderId in enumerate(transponderIds):
        gate = gates[index]

        if transponderId == "T_002":
            timestamp = "1798693200000"  # 2016-12-31 in milliseconds EXPIRY CASE
        else:
            timestamp = str(int(time.time() * 1000))

        # Send the message
        send_message(transponderId, gate, lot, timestamp)
        time.sleep(2)


def callback(ch, method, properties, body):
        """
        Handle the deserialized message
        {"action": "OPEN_GATE", "payload": {"gate": "E2", "lot": "A"}},
        """
        try:
            data = json.loads(body)
            action = data.get('action')
            payload = data.get('payload', {})

            if action == "OPEN_GATE":
                print(f"The Physical gate {payload.get('gate')} in lot {payload.get('lot')} is opening")
            elif action == "NOT_OPEN_GATE":
                print(f"Something went wrong with gate {payload.get('gate')} in lot {payload.get('lot')}")
            else:
                print(f"Unknown action: {action}")
        except json.JSONDecodeError:
            print(f"Invalid JSON message: {body}")
        except Exception as e:
            print(f"Error processing message: {e}")


if __name__ == "__main__":
    print("This is simulating two gates(E1 E2) reading transponders ")
    print("T_001 is VALID, but T_022 is expired")
    channel.basic_consume(queue='gate.command.queue', on_message_callback=callback, auto_ack=True)
    simulate_requests()
    channel.start_consuming()
