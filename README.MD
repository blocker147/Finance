## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)

## Overview

Spring Boot Finance aggregator backend service that exposes API, and allows a customer to fill in application form 
and submit data to service. It sends customer application data to two financing institutions (FastBank and SolidBank), 
that will provide an offer for this customer (in some cases financing institution can't offer anything to customer).

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/blocker147/Finance.git
    cd Finance
    ```

2. Build the project using Maven:

    ```bash
    mvn clean compile
    ```
