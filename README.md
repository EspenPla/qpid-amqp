# Read AMQP 1.0 messages as a json source

[![Build Status](https://travis-ci.org/sesam-community/qpid-amqp.svg?branch=master)](https://travis-ci.org/sesam-community/qpid-amqp)

Example system config:
```
{
  "_id": "qpid-trafikkdata",
  "type": "system:microservice",
  "docker": {
    "environment": {
      "client_certificate": "MIIJfgIBAzC[..]GoA==",
      "client_certificate_password": "<password>",
      "url": "amqps://interchange-test.nordic-way.io?transport.trustAll=true"
    },
    "image": "sesamcommunity/qpid-amqp",
    "port": 8080
  }
}

```

Example source pipe:
```
{
  "source": {
    "type": "json",
    "system": "rabbitmq-trafikkdata",
    "is_chronological": true,
    "supports_since": true,
    "url": "/myqueue"
  }
}

```

Example output:
```
$ curl localhost:5000/?limit=1 | jq .
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  4450    0  4450    0     0   101k      0 --:--:-- --:--:-- --:--:--  101k
[
  {
    "_id": "c5e80bc5-34ff-4d5f-ad0e-46ae39a66b0f",
    "payload": " foo"
  }
]
```

## Limitations

* Acknowledges message before guaranteed delivery (!)
