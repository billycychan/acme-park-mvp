# ACME PARK! MVP

- Authors: Billy Chan
- Version: 2024.10

## Abstract
This repository is the MVP for *Case Study: The ACME Park! product* demonstrating the situation: 

members present their transponder at the gate, and the gate is opened if they have a valid permit.

## Overall Description

- `infrastructure`: contains the _technical_ services used to support the ACME PARK! MVP
    - `message-broker`: A RabbitMQ server to support event-driven architectures using MQTT and AMQP protocols.
      - AMQP was used in this project
    - `service-registry`: An Eureka server (Netflix) to support service discovery
      - Message Queue was used for inter-service communication
- `services`: The business services used to support ACME PARK! MVP
    - `gate-service`: service to receive transponder access request sent by client and forward them to permit service for validation
    - `permit-service` service to receive validation request sent by `gate-service` and give back the result to gate-service
- `clients`: External (non-service-based artefacts) consuming the ACME PARK! services
    - `gate-transponder-scanner-client`: a simulator pretending transponder scanned event sending data to the  `gate-service`
## Services location

**If you're on a Mac**, you might encounter issues using `localhost` (it might take some time to be available, or be blocked once and foor all). You can always refer to the direct IP address of localhost (`127.0.0.1`) to bypass this situation.

- Infrastructure:
    - RabbitMQ Management web interface: <http://localhost:8080> (login: `admin`, password: `cas735`)
    - Eureka registry: <http://locahost:8761>
- Business Services:
    - Gate Service <http://locahost:8081>
    - Permit Service <http://localhost:8082>

## How to operate?

### Compiling the micro-services

```
acme-park-mvp $ mvn clean package
```

### Building the turn-key services

```
acme-park-mvp $ cd deployment
deployment $ docker compose build --no-cache
```

### Starting the complete system

```
deployment $ docker compose up -d
```

### Shutting down the system

```
deployment $ docker compose down
```

## Overall Flowchart

### Gate Access Service
```mermaid
flowchart LR

    REST_IN([fa:fa-circle-right POST Request fa:fa-envelope \n transponderId, gate, lot ])

    AMQP_IN([fa:fa-circle-right AMQP fa:fa-envelope\n permit.validated.response.queue])
    AMQP_OUT([fa:fa-envelope AMQP fa:fa-circle-right\n permit.validated.request.queue])


    subgraph "<< gate-access-service >>"
        REQUEST_IN("<< InputPort >>\n RequestReceiver")
        RESPONSE_IN("<< InputPort >>\n AccessResult")
        SERVICE{{"\nGateAccess\nService\n<br>"}}
        HRM_SENDER("<< OutputPort >>\n RequestSender")
    end

    REST_IN -- TransponderAccessRequest--> REQUEST_IN
    AMQP_IN -- AccessResult --> RESPONSE_IN
    REQUEST_IN -. offered .- SERVICE
    RESPONSE_IN -. offered .- SERVICE


SERVICE -. required .- HRM_SENDER
HRM_SENDER -- TransponderAccessRequest --> AMQP_OUT
```
### Permit Service
```mermaid
flowchart LR

    AMQP_IN([fa:fa-envelope AMQP fa:fa-circle-right\n permit.validated.request.queue])
    AMQP_OUT([fa:fa-envelope AMQP fa:fa-circle-right\n permit.validated.response.queue])


    subgraph "<< permit-service >>"
        RESPONSE_IN("<< InputPort >>\n RequestReceiver")
        SERVICE{{"\nPermit\nService\n<br>"}}
        RESPONSE_OUT("<< OutputPort >>\n ResponseSender")
    end

    AMQP_IN -- TransponderAccessRequest --> RESPONSE_IN
    RESPONSE_IN -. offered .- SERVICE


SERVICE -. required .- RESPONSE_OUT
RESPONSE_OUT -- TransponderAccessRequest --> AMQP_OUT

```

## Architecture Deep Dive
### Gate Access Service
```mermaid
classDiagram
  namespace InboundPorts {
    class RequestReceiver {
      <<Interface>>
      + receive(TransponderAccessRequest request)
    }

    class ResponseReceiver {
      <<Interface>>
      + receive(ResponderReceiver)
    }
  }

  namespace InboundAdapters {
    class GateListener {
      + handlePermitValidated(AccessResult result)
    }

    class GateAccessRESTController {
      + createTransponderRequest(@RequestBody TransponderAccessRequest request): ResponseEntity<?>
    }
  }

  namespace OutboundPorts {
    class RequestSender {
      <<Interface>>
      + send(TransponderAccessRequest request);
    }

    class GateManagement {
      <<Interface>>
      + handle(AccessResult result);
    }

  }

  namespace OutboundAdapters {
    class GatePublisher {
      + send(TransponderAccessRequest request)
    }

    class GateManager {
      - openGate(String transponderId, String lot, String gate)
      - showError(String transponderId, String lot, String gate, String message)
    }
  }

  namespace Business {
    class GateAccessBusiness {

    }
  }

  class AccessResult {
    <<DTO>>
    + transponderId: String
    + gate: String
    + lot: Int
    + event: PermitValidatedEvent
    + message: String
  }

  class PermitValidatedEvent {
    <<DTO>>
    VALIDATED
    EXPIRED
    NOT_REGISTERED
    TIMEOUT
  }

  class TransponderAccessRequest {
    <<DTO>>
    + transponderId: String
    + gate: String
    + lot: String
    + datetime: timestamp
  }

  GateListener --> ResponseReceiver
  GateManagement <|.. GateManager
  RequestSender <|.. GatePublisher
  RequestReceiver  <|.. GateAccessBusiness
  ResponseReceiver  <|.. GateAccessBusiness
  GateAccessBusiness --> RequestSender
  GateAccessBusiness --> GateManagement

```
### Permit Service

```mermaid
classDiagram
    namespace InboundPorts {
        class RequestReceiver {
            <<Interface>>
            + receive(TransponderAccessRequest request)
        }

        class RequestValidator {
            <<Interface>>
            + validateTransponderRequest(TransponderAccessRequest request): AccessResult
        }
    }

    namespace InboundAdapters {
        class PermitValidationService {
            + permitRepository: PermitRepository
            - isExpired(Permit permit, TransponderAccessRequest request): boolean
        }

        class AMQPListener {
            + requestReceiver: RequestReceiver
            + receiveTransponderAccessRequest(TransponderAccessRequest request)
        }
    }

    namespace OutboundPorts {
        class PermitRepository {
            <<Interface>>
            + findByTransponderId(String transponderId): Optional<Permit>
        }

        class ResponseSender {
            <<Interface>>
            + send(AccessResult result);
        }
    }

    namespace OutboundAdapters {
        class HardcodePermitRepository {
            + List<Permit> permits
        }

        class AMQPSender {
        }
    }

    namespace Business {
        class Translator {
            responseSender: ResponseSender
            requestValidator: RequestValidator
        }
    }

    class AccessResult {
        <<DTO>>
        + transponderId: String
        + gate: String
        + lot: Int
        + event: PermitValidatedEvent
        + message: String
    }

    class Permit {
        <<DTO>>
        + permitId: String
        + transponderId: String
        + memberId: String
        + licensePlates: List<String>
        + expiryDate: timestamp
    }

    class PermitValidatedEvent {
        <<DTO>>
        VALIDATED
        EXPIRED
        NOT_REGISTERED
        TIMEOUT
    }

    class TransponderAccessRequest {
        <<DTO>>
        + transponderId: String
        + gate: String
        + lot: String
        + datetime: timestamp
    }

    AMQPListener --> RequestReceiver
    ResponseSender <|-- AMQPSender
    PermitRepository <|-- HardcodePermitRepository
    PermitValidationService --> PermitRepository
    PermitValidationService <|-- RequestValidator
    RequestReceiver <|-- Translator
    Translator --> ResponseSender
    Translator --> RequestValidator

```