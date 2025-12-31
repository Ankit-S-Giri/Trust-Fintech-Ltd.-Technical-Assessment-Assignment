# Trust Fintech Ltd. Technical-Assessment-Assignment

## Overview

This project implements a high-throughput transaction processing system designed to handle **up to 8,000 concurrent requests per second (RPS)** with zero failure under peak load.

---

## System Architecture

The system consists of:

* **Two independent client services**

  * Bank A
  * Bank B
* **One central server service**

---

## Client Project Structure (Bank A & Bank B)

Each client service includes:

* REST API layer (receives JSON requests)
* Transaction ID generator
* JSON → XML conversion layer
* Concurrent request executor
* Server communication layer

---

## Server Project Structure

The server service includes:

* API layer to accept XML requests
* XML unmarshalling layer
* Multithreaded transaction processor
* Database persistence layer for transaction logs
* Response generator

---

## Repository Structure & Data Flow

```
Client (Bank A / Bank B)
        |
        |  JSON (REST)
        v
Client API Layer
        |
        |  XML (HTTP)
        v
Server API Layer
        |
        |  JPA / Hibernate
        v
MySQL / H2 Database
```

---

## Concurrency Design

### Asynchronous Architecture

**Client Side**

* Clients do not block while waiting for server responses.
* A custom `ThreadPoolTaskExecutor` is used to **fire-and-forget** requests.
* Clients immediately return a `FORWARDED` status to the user.

**Server Side**

* HTTP request processing is decoupled from database writes.
* Database logging runs on a **separate thread pool**, preventing I/O latency from impacting request throughput.

---

### Thread Pool & Queue Configuration

Default Spring Boot settings (≈200 threads) are insufficient for high-load scenarios.

Custom executor configuration:

* **Core Pool Size:** 100+ threads to keep CPU cores fully utilized
* **Queue Capacity:** 100,000 to absorb traffic bursts without rejection

---

### Connection Pooling Strategy

* Apache **HttpClient 5** with connection pooling is used instead of `RestTemplate`.
* Opening and closing 8,000 TCP connections per second leads to **port exhaustion**.
* Connection pooling reuses existing connections, significantly reducing:

  * CPU overhead
  * Socket creation latency
  * OS-level resource exhaustion

---

## Load Distribution Strategy

The system is designed to process traffic from multiple banks concurrently.

* **Bank A:** Accepts traffic on port `8081`
* **Bank B:** Accepts traffic on port `8082`
* **Server:** Aggregates and processes requests from both clients concurrently using thread pools

---

## Load Test Results (Success vs Failure)

During the peak load test of **8,103 RPS**, the system demonstrated complete stability.

| Metric  | Count   | Percentage | Description                                                    |
| ------- | ------- | ---------- | -------------------------------------------------------------- |
| Success | 244,427 | 100.00%    | All requests returned `HTTP 200 OK` with ~22ms average latency |
| Failure | 0       | 0.00%      | No timeouts, connection refusals, or server errors             |

---

## Processing Latency

The system maintained low latency even under extreme load.

| Metric       | Value    | Description                                                 |
| ------------ | -------- | ----------------------------------------------------------- |
| Average Time | 22 ms    | Response time for ~99% of transactions                      |
| Peak Time    | 1,980 ms | Maximum delay observed during thread pool saturation spikes |

---

## Observations During High Load Testing

### 1. Thread Thrashing Threshold

**Observation**
Testing with 2,000+ concurrent threads resulted in:

* Throughput ≈ 900 RPS
* Latency ≈ 12 seconds

**Analysis**
The system hit a **context switching bottleneck**, where CPU cycles were wasted switching threads instead of executing logic.

**Solution**
Reducing client thread pools to **~200 active threads** eliminated thrashing and restored throughput.

---

### 2. MySQL vs H2 — Persistence Bottleneck

**Observation**
Using disk-based MySQL on localhost capped throughput at ~1,200 RPS.

**Analysis**
Disk I/O became the system bottleneck; application logic outpaced storage writes.

**Solution**
For load-testing purposes, persistence was switched to **H2 In-Memory Database**, removing disk latency from the critical path.

---

### 3. Backpressure with `CallerRunsPolicy`

**Observation**
At extreme burst loads (10,000+ requests in milliseconds), executor queues filled instantly.

**Analysis**
Using the default `AbortPolicy` would result in:

* `TaskRejectedException`
* HTTP 500 errors

**Solution**
Implementing `CallerRunsPolicy` applied **natural backpressure**, slowing request intake without dropping requests.

---

## Setup, Run & Test Instructions

### 1. Prerequisites

Ensure the following are installed:

* Java 17
* Maven
* Apache JMeter
* Git

---

### 2. Clone, Build & Run Services

```bash
git clone https://github.com/Ankit-S-Giri/Trust-Fintech-Ltd.-Technical-Assessment-Assignment.git
```

#### Start Server

```bash
cd server
mvn clean install spring-boot:run
```

#### Start Client – Bank A

```bash
cd client-bank-a
mvn clean install spring-boot:run
```

#### Start Client – Bank B

```bash
cd client-bank-b
mvn clean install spring-boot:run
```

---

### 3. Load Testing (8,000 RPS Verification)

1. Open **Apache JMeter**
2. Click **File → Open**
3. Select `requests/JMeter-Test.jmx`
4. Run the test
5. Observe the **Summary Report Listener**

---

## Expected Results

* **Throughput:** > 8,000 requests/sec
* **Average Latency:** < 50 ms
* **Error Rate:** 0.00%
