import requests
import json
import time
import random
from datetime import datetime, timedelta

# Constants for generating data
TRANSPONDER_IDS = [f"T_{str(i).zfill(3)}" for i in range(1, 11)] + ["T_UNKNOWN"] +["T_999"]
GATES = ["ENTRY_1", "ENTRY_2"]  # Only two gates
LOT = "A"  # Only lot A
EXPIRATION_DATE = datetime(2025, 12, 31, 23, 59)


# Function to generate random datetime before or after expiration date
def generate_random_datetime():
    # Randomly decide whether the datetime is before or after the expiration date
    if random.choice([True, False]):
        # Generate a datetime before expiration
        max_date = EXPIRATION_DATE - timedelta(days=random.randint(1, 365))
        random_datetime = max_date - timedelta(hours=random.randint(0, 23),
                                               minutes=random.randint(0, 59),
                                               seconds=random.randint(0, 59))
    else:
        # Generate a datetime after expiration
        min_date = EXPIRATION_DATE + timedelta(days=random.randint(1, 365))
        random_datetime = min_date + timedelta(hours=random.randint(0, 23),
                                               minutes=random.randint(0, 59),
                                               seconds=random.randint(0, 59))
    return random_datetime.strftime("%Y-%m-%d %H:%M:%S")


# Function to send POST request
def send_post_request(transponder_id, gate):
    url = "http://localhost:8081/gate/validate"

    # Since only "A" lot exists
    lot = "A"

    # Generate a random datetime (before or after the expiration date)
    datetime_str = generate_random_datetime()

    # Create the payload
    payload = {
        "transponder-id": transponder_id,
        "gate": gate,
        "lot": lot,
        "datetime": datetime_str
    }

    # Send POST request
    try:
        response = requests.post(url, json=payload)
        print(f"Sent: {json.dumps(payload)} - Response: {response.status_code}")
    except Exception as e:
        print(f"Error sending request: {e}")


# Function to send requests in the specified order
def send_requests_sequentially():
    while True:
        for transponder_index in range(len(TRANSPONDER_IDS)):
            transponder_id = TRANSPONDER_IDS[transponder_index]

            # Determine gate based on transponder index
            if transponder_index % 2 == 0:
                gate = "ENTRY_1"
            else:
                gate = "ENTRY_2"

            # Send request
            send_post_request(transponder_id, gate)
            time.sleep(2)  # Wait for 1 second between requests


if __name__ == "__main__":
    send_requests_sequentially()