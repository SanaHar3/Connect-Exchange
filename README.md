# 💱 Connect-Exchange

## Contexte & Objectif

Votre entreprise a souscrit un abonnement auprès d’un fournisseur de taux de change.  
Chaque appel à l’API de ce fournisseur est facturé.  
Actuellement, environ 15 000 équipes internes utilisent leur propre abonnement, ce qui entraîne une augmentation considérable des coûts.

Pour optimiser cela, votre équipe a été missionnée pour développer une application intermédiaire (un proxy), chargée de centraliser les appels à l’API du fournisseur.  
Cette application se chargera de :

- Récupérer les taux de change via l’API externe :  
  Exemple : `https://api.exchangerate-api.com/v4/latest/USD`
- Publier les taux en temps réel sur un topic Kafka
- Les stocker dans Elasticsearch
- Permettre leur visualisation via Kibana, à travers un tableau de bord

Les autres équipes internes pourront alors consommer les données depuis Kafka, sans avoir besoin d’accéder directement à l’API externe, ce qui réduit les coûts.

---

## Stack Technique

- Backend : Java / Spring Boot  
- Streaming : Apache Kafka  
- Indexation : Elasticsearch  
- Visualisation : Kibana  
- Docker (Elastic, Kibana et Kafka)

---

## Fonctionnalités principales

- Appel périodique à l’API de taux de change
- Publication des taux en temps réel sur un topic Kafka (`mon-tunnel-topic`)
- Indexation des données dans Elasticsearch (`connect-exchange`)
- Visualisation des données dans Kibana

---

### 1. Lancer Elasticsearch & Kibana

Commande :
```bash
docker-compose up -d
```

Accès :
- Elasticsearch : http://localhost:9200  
- Kibana : http://localhost:5601

---

### 3. Lancer Kafka et créer le topic Kafka

Commande :
```bash
docker exec -it kafka kafka-topics --create --topic mon-tunnel-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

---

### 4. Configurer l’application

Fichier à modifier : `application.properties`

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=tp-kafka-step1
spring.data.elasticsearch.uris=http://localhost:9200
```

---

### 5. Lancer l’application Spring Boot

```bash
mvn clean package
run app
```

---

### 6. Tester un appel API

```bash
curl -X POST "http://localhost:8080/connect-exchange?content=USD"
```

Ou commande PowerShell

```bash
Invoke-WebRequest -Uri "http://localhost:8080/connect-exchange?content=USD" -Method POST
```

---

## Visualisation dans Kibana

1. Aller dans Kibana > Stack Management > Data Views  
2. Créer une vue basée sur l’index `connect-exchange`  
3. Visualiser le dashboard `connect-exchange`

---

## 

Développé par : Snekha Harikrishnan
