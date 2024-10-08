# Technical Infrastructure

- Author: Billy Chan
- Version: 2024.10

## Rationale

This artefact describes the technical infrastructure (message broker and service registry) used by ACME PARK! MVP

## Technologies

- Docker compose

## How to use?

To build all the containers:

```
deployment $ docker compose build --no-cache
```

To deploy the system:

```
deployment $ docker compose up -d
```

To shut down the system:

```
deployment $ docker compose stop
```
