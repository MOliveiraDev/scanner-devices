# Scanner-devices

<hr>

<img src="https://github.com/MOliveiraDev/scanner-devices/blob/main/scannerdevices-service/assets/ScannerDevices-Diagrama.jpg"></img>

### Projeto utilizando Kotlin para gerenciamento e escaneamento de dispositivos conectados a roteadores.

<hr>

## Descrição

O `scanner-devices` é uma API REST desenvolvida com Spring Boot que permite listar roteadores, dispositivos conectados e realizar operações de consulta sobre a rede. O sistema expõe endpoints para consulta de dispositivos por roteador e listagem geral.

## Utilização

- Listar todos os roteadores cadastrados
- Listar dispositivos conectados a um roteador específico
- Listar todos os dispositivos de todos os roteadores
- Scannear detalhes de dispositivos e roteadores

## Tecnologias Utilizadas

- Kotlin
- Spring Boot (Web, Data JPA)
- Maven
- MySQL


## Endpoints e Resposta

### `GET /Routers`

Lista todos os roteadores cadastrados.

**Resposta:**
```json
[
  {
    "id": 1,
    "ssid": "MinhaRede",
    "protocol": "802.11ac",
    "securityType": "WPA2"
  },
  {
    "id": 2,
    "ssid": "OutraRede",
    "protocol": "802.11n",
    "securityType": "WPA"
  }
]
```

### `GET /Devices/{routerId}`

Lista os dispositivos conectados ao roteador informado.

**Resposta:**
```json
[
  {
    "ip": "192.168.0.10",
    "routerId": 1,
    "device": "Notebook",
    "connected": true,
    "lastConnection": "2024-06-10T14:23:00"
  },
  {
    "ip": "192.168.0.11",
    "routerId": 1,
    "device": "Smartphone",
    "connected": false,
    "lastConnection": "2024-06-09T20:10:00"
  }
]
```

### `POST /scan-devices`

Scanneia novo dispositivo a um roteador.


<hr>

## Como rodar a aplicação

### Requisitos
- Obter o docker baixado no seu dispositivo
- Baixar o docker `docker-compose.yml` na pasta raiz do repositório

### Rodar com o Docker
- Entra na pasta onde baixou o `docker-compose.yml` e vai abrir com o terminal
- Agora execute com esse comando:
```sh
docker-compose up -d
```
