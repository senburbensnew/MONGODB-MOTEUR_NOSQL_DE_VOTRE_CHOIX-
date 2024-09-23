// Spécification des Modèles de Documents et des Classes JAVA pour MongoDB et Cassandra
// 1. Modèles de Documents pour MongoDB

// MongoDB est un système de base de données NoSQL orienté document, qui stocke les données sous forme de documents JSON/BSON. Les objets sont donc organisés en collections et chaque document peut être indépendant en termes de structure.

// a. Collection "Medecin" (document JSON)

{
  "_id": "ObjectId",
  "numero_securite_sociale": "String",
  "nom": "String",
  "sexe": "String",
  "date_naissance": "Date",
  "specialite": "String",
  "email": "String",
  "cv": "String",
  "adresse": {
    "numero": "Number",
    "rue": "String",
    "code_postal": "Number",
    "ville": "String"
  },
  "list_telephones": ["String"],
  "list_prenoms": ["String"],
  "list_rendez_vous": [
    {
      "patient": "ObjectId",
      "date_rendez_vous": "Date",
      "motif": "String"
    }
  ],
  "list_consultations": [
    {
      "patient": "ObjectId",
      "raison": "String",
      "diagnostic": "String",
      "date_consultation": "Date",
      "list_examens": [
        {
          "details_examen": "String",
          "date_examen": "Date"
        }
      ],
      "list_prescriptions": [
        {
          "details_prescription": "String",
          "date_prescription": "Date"
        }
      ]
    }
  ]
}


{
    "_id": "ObjectId",
	"numero_securite_sociale" : "String",
    "nom": "String",
	"sexe" : "String",
	"date_naissance" : "Date",
    "specialite": "String",
    "email": "String",
	"cv" : "String",
    "adresse": {
	  "numero": "Number"
      "rue": "String",
      "code_postal": "Number",
	  "ville": "String"
    },
	"list_telephones": ["String"],
	"list_prenoms" : ["String"],
	"list_rendez_vous" : [
		{	
			"patient": "ObjectId",  // Référence à Patient
			"date_rendez_vous": "Date",
			"motif": "String",
		}
	]
	"list_consultations" : [
		{
			"patient": "ObjectId",  // Référence à Patient
			"raison" : "String",
			"diagnostic" : "String",
			"date_consultation" : "Date",
			"list_examens" : [
				{
					"details_examen" : "String",
					"date_examen" : "Date"
				}
			],
			"list_prescriptions" : [
				{
					"details_prescription" : "String",
					"date_prescription" : "Date"
				}
			]
		}
	]
 }

// b. Collection "Patient" (document JSON)

{
  "_id": "ObjectId",
  "numero_securite_sociale": "String",
  "nom": "String",
  "sexe": "String",
  "date_naissance": "Date",
  "email": "String",
  "poids": "Number",
  "hauteur": "Number",
  "list_telephones": ["String"],
  "list_prenoms": ["String"],
  "adresse": {
    "numero": "Number",
    "rue": "String",
    "code_postal": "Number",
    "ville": "String"
  },
  "list_rendez_vous": [
    {
      "medecin": "ObjectId",
      "date_rendez_vous": "Date",
      "motif": "String"
    }
  ],
  "list_consultations": [
    {
      "medecin": "ObjectId",
      "raison": "String",
      "diagnostic": "String",
      "date_consultation": "Date",
      "list_examens": [
        {
          "details_examen": "String",
          "date_examen": "Date"
        }
      ],
      "list_prescriptions": [
        {
          "details_prescription": "String",
          "date_prescription": "Date"
        }
      ],
      "facture": {
        "Montant_Total": "Number",
        "date_facture": "Date"
      }
    }
  ],
  "antecedents_medicaux": [
    {
      "type": "String",
      "description": "String",
      "date": "Date"
    }
  ],
  "allergies": ["String"]
}


{
	"_id": "ObjectId",
	"numero_securite_sociale" : "String",
    "nom": "String",
	"sexe" : "String",
	"date_naissance" : "Date",
    "email": "String",
	"poids" : "Number",
	"hauteur" : "Number",
	"list_telephones": ["String"],
	"list_prenoms" : ["String"],
    "adresse": {
	  "numero": "Number"
      "rue": "String",
      "code_postal": "Number",
	  "ville": "String"
    },
	"list_rendez_vous" : [
		{	
			"medecin": "ObjectId",  // Référence au medecin
			"date_rendez_vous": "Date",
			"motif": "String",
		}
	],
	"list_consultations" : [
		{
			"medecin": "ObjectId",  // Référence au medecin
			"raison" : "String",
			"diagnostic" : "String",
			"date_consultation" : "Date",
			"list_examens" : [
				{
					"details_examen" : "String",
					"date_examen" : "Date"
				}
			],
			"list_prescriptions" : [
				{
					"details_prescription" : "String",
					"date_prescription" : "Date"
				}
			],
			"facture" : {
				"Montant_Total" : "Number",
				"date_facture" : "Date"
			}
		}
	],
    "antecedentsMedicaux": [
      {
        "type": "String",
        "description": "String",
        "date": "Date"
      }
    ],
    "allergies": ["String"]
}


// 2. Modèles de Données pour Cassandra
// Cassandra est une base de données NoSQL orientée colonnes, idéale pour les applications nécessitant une gestion distribuée et une tolérance aux pannes. Les données sont organisées sous forme de tables, mais ne suivent pas un modèle strictement relationnel.

// a. Table "Medecin"
CREATE TABLE Medecin (
  id UUID PRIMARY KEY,
  numero_securite_sociale text,
  nom text,
  sexe text,
  date_naissance date,
  specialite text,
  email text,
  cv text,
  adresse FROZEN<map<text, text>>,
  list_telephones list<text>,
  list_prenoms list<text>,
  list_rendez_vous list<FROZEN<tuple<UUID, date, text>>>,
  list_consultations list<FROZEN<tuple<UUID, text, text, date, list<FROZEN<tuple<text, date>>>, list<FROZEN<tuple<text, date>>>>>>>
);

// b. Table "Patient"
CREATE TABLE Patient (
  id UUID PRIMARY KEY,
  numero_securite_sociale text,
  nom text,
  sexe text,
  date_naissance date,
  email text,
  poids double,
  hauteur double,
  list_telephones list<text>,
  list_prenoms list<text>,
  adresse FROZEN<map<text, text>>,
  list_rendez_vous list<FROZEN<tuple<UUID, date, text>>>,
  list_consultations list<FROZEN<tuple<UUID, text, text, date, list<FROZEN<tuple<text, date>>>, list<FROZEN<tuple<text, date>>>, FROZEN<tuple<double, date>>>>>,
  antecedents_medicaux list<FROZEN<tuple<text, text, date>>>,
  allergies list<text>
);


// 3. Spécification des Classes et Méthodes JAVA pour MongoDB
// a. Méthodes CRUD

// Méthode pour insérer un medecin
public void insertMedecin(Medecin medecin) {
    MongoCollection<Document> medecinCollection = database.getCollection("Medecin");
    Document doc = new Document("numero_securite_sociale", medecin.getNumeroSecuriteSociale())
                      .append("nom", medecin.getNom())
                      .append("sexe", medecin.getSexe())
                      .append("date_naissance", medecin.getDateNaissance())
                      .append("specialite", medecin.getSpecialite())
                      .append("email", medecin.getEmail());
    medecinCollection.insertOne(doc);
}

// Méthode pour lire un medecin par son id
public Medecin getMedecinById(String id) {
    MongoCollection<Document> medecinCollection = database.getCollection("Medecin");
    Document query = new Document("_id", new ObjectId(id));
    Document result = medecinCollection.find(query).first();
    if (result != null) {
        return new Medecin(result.getString("nom"), result.getString("specialite"));
    }
    return null;
}

// Méthode pour mettre à jour un medecin
public void updateMedecin(String id, String email) {
    MongoCollection<Document> medecinCollection = database.getCollection("Medecin");
    medecinCollection.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.set("email", email));
}

// Méthode pour supprimer un medecin
public void deleteMedecin(String id) {
    MongoCollection<Document> medecinCollection = database.getCollection("Medecin");
    medecinCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
}

// b. Indexation secondaire
// Création d'index secondaire sur le champ "nom"
medecinCollection.createIndex(Indexes.ascending("nom"));

// c. Méthode de Consultation (Jointure, Groupement)
// Groupement des medecins par specialité
public void groupMedecinsBySpecialite() {
    MongoCollection<Document> medecinCollection = database.getCollection("Medecin");
    AggregateIterable<Document> groupResult = medecinCollection.aggregate(Arrays.asList(
        Aggregates.group("$specialite", Accumulators.sum("total", 1))
    ));
    
    for (Document doc : groupResult) {
        System.out.println(doc.toJson());
    }
}

// 4. Spécification des Classes et Méthodes JAVA pour Cassandra
// a. Méthodes CRUD
// Insertion d'un medecin
public void insertMedecin(Medecin medecin) {
    session.execute("INSERT INTO Medecin (id, numero_securite_sociale, nom, specialite) VALUES (?, ?, ?, ?)",
                    UUID.randomUUID(), medecin.getNumeroSecuriteSociale(), medecin.getNom(), medecin.getSpecialite());
}

// Lecture d'un medecin
public Medecin getMedecinById(UUID id) {
    ResultSet rs = session.execute("SELECT * FROM Medecin WHERE id = ?", id);
    Row row = rs.one();
    if (row != null) {
        return new Medecin(row.getString("nom"), row.getString("specialite"));
    }
    return null;
}

// Mise à jour d'un medecin
public void updateMedecin(UUID id, String email) {
    session.execute("UPDATE Medecin SET email = ? WHERE id = ?", email, id);
}

// Suppression d'un medecin
public void deleteMedecin(UUID id) {
    session.execute("DELETE FROM Medecin WHERE id = ?", id);
}

// b. Indexation Secondaire

// Création d'un index sur le champ "nom"
session.execute("CREATE INDEX IF NOT EXISTS ON Medecin (nom)");

// c. Jointure et Groupement
Dans Cassandra, les jointures ne sont pas supportées directement, donc il est recommandé d'organiser les données pour éviter les jointures en dénormalisant les données. Vous pouvez utiliser la requête de sélection par clé de partition ou le groupement via des agrégats externes.

Ces spécifications montrent comment modéliser et interagir avec les bases de données MongoDB et Cassandra, en tenant compte des particularités de chaque moteur.



4) ### MongoDB vs Cassandra: Analyse Théorique et Pratique

MongoDB et Cassandra sont deux bases de données NoSQL populaires, mais elles diffèrent considérablement dans leur architecture, leur modèle de données, et leurs cas d'utilisation. Voici une comparaison détaillée entre les deux moteurs sur les différents points que vous avez mentionnés.

---

### 1. **Modèles de données supportés**

- **MongoDB**:
  - **Modèle de données**: Documentaire (JSON/BSON)
  - MongoDB stocke les données sous forme de documents JSON (ou BSON, une version binaire de JSON). Chaque document est un ensemble de paires clé-valeur. Cela permet de gérer des structures complexes, des tableaux et des sous-documents imbriqués. Le modèle est flexible, sans schéma strict (schema-less).
  - **Usage**: Convient aux cas où la structure des données peut varier ou être modifiée fréquemment.

- **Cassandra**:
  - **Modèle de données**: Colonne familiale
  - Cassandra utilise un modèle de colonne-clé, où les données sont organisées en lignes et colonnes. C'est un modèle plus proche des bases de données relationnelles, mais avec une architecture distribuée et sans transactions complexes comme celles des bases SQL.
  - **Usage**: Idéal pour les applications qui nécessitent des écritures à haute vitesse et des lectures avec des clés spécifiques (par exemple, journaux, métadonnées).

---

### 2. **Procédure d’installation du moteur et des utilitaires**

- **MongoDB**:
  - **Installation**: Relativement simple. MongoDB offre des packages pour les systèmes d'exploitation populaires (Linux, Windows, macOS). Il suffit d'installer MongoDB et de démarrer le service MongoDB (`mongod`).
  - **Utilitaires**: Outils de ligne de commande comme `mongo`, `mongodump`, et `mongoimport`. MongoDB Compass offre une interface utilisateur graphique pour interagir avec les données.

  **Installation (Linux)**:
  ```bash
  sudo apt-get install -y mongodb
  sudo systemctl start mongodb
  ```

- **Cassandra**:
  - **Installation**: L'installation de Cassandra peut être plus complexe, en particulier sur des clusters distribués. Elle nécessite l'installation de Java, puis Cassandra via les distributions officielles.
  - **Utilitaires**: Cassandra utilise `cqlsh`, un utilitaire en ligne de commande pour interagir avec la base de données à l'aide de CQL (Cassandra Query Language). L'administration de Cassandra peut également se faire via `nodetool`.

  **Installation (Linux)**:
  ```bash
  sudo apt install cassandra
  sudo systemctl start cassandra
  ```

---

### 3. **Architecture du moteur NoSQL (schéma)**

- **MongoDB**:
  - **Architecture**: MongoDB suit une architecture maître-esclave (ou primaire-secondaire) dans un ensemble de répliques. Les données sont partitionnées en fragments (sharding) pour faciliter la scalabilité horizontale. Les fragments sont répartis entre plusieurs serveurs.
  - **Schéma**:
    - Sharding pour le partitionnement.
    - Réplication pour la haute disponibilité.
    - Chaque ensemble de répliques a un **primaire** qui gère les écritures.

    ![Architecture MongoDB](https://www.mongodb.com/docs/manual/_images/sharded-cluster.png)

- **Cassandra**:
  - **Architecture**: Cassandra a une architecture distribuée peer-to-peer, où tous les nœuds sont égaux. Il n’y a pas de maître, chaque nœud peut accepter des lectures et des écritures. Cette architecture est conçue pour la haute disponibilité et l'évolutivité.
  - **Schéma**:
    - Les nœuds sont connectés dans un anneau.
    - Pas de nœud central (évitant le single point of failure).
    - Lecture/écriture à haute disponibilité.

    ![Architecture Cassandra](https://cassandra.apache.org/_/img/cassandra-architecture.svg)

---

### 4. **Méthode de partitionnement (schéma)**

- **MongoDB**:
  - **Sharding**: MongoDB utilise un mécanisme de **sharding** pour partitionner horizontalement les données. Les documents sont distribués entre différents fragments en fonction de la **clé de sharding**.
  - **Schéma**:
    - Clé de sharding choisie pour diviser les documents.
    - Chaque fragment stocke une portion des données.

    ![MongoDB Sharding](https://www.mongodb.com/docs/manual/_images/sharding-architecture.png)

- **Cassandra**:
  - **Partitionnement**: Cassandra utilise le **partitionnement par clé** pour répartir les données entre les nœuds du cluster. Une clé de partition est choisie, et les données sont distribuées uniformément en utilisant un **hash** de la clé.
  - **Schéma**:
    - Chaque nœud stocke une plage de valeurs de clé.
    - Le partitionneur détermine quel nœud recevra quelles données.

    ![Cassandra Partitioning](https://docs.datastax.com/en/cassandra-oss/3.0/cassandra/architecture/img/replicationPartitionerDiagram.png)

---

### 5. **Méthode de réplication (schéma)**

- **MongoDB**:
  - **Réplication**: MongoDB utilise des **répliques** pour la haute disponibilité. Un ensemble de répliques contient un **primaire** et plusieurs **secondaires**. Le primaire gère les écritures, et les réplicas (secondaires) répliquent les données.
  - **Schéma**:
    - Primaire pour les écritures.
    - Secondaires pour la redondance et les lectures.

    ![MongoDB Replica Set](https://www.mongodb.com/docs/manual/_images/replica-set-read-write-operations-primary.bakedsvg.svg)

- **Cassandra**:
  - **Réplication**: Cassandra utilise un modèle de réplication où chaque nœud réplique les données vers **N** autres nœuds en fonction du facteur de réplication.
  - **Schéma**:
    - Réplication en anneau, avec chaque nœud répliquant les données sur les nœuds voisins.

    ![Cassandra Replication](https://cassandra.apache.org/_/img/replication-strategy.png)

---

### 6. **Montée en charge (schéma)**

- **MongoDB**:
  - MongoDB permet une montée en charge horizontale grâce au **sharding**. En ajoutant de nouveaux serveurs, les fragments peuvent être redistribués pour mieux répartir la charge.
  - **Schéma**:
    - Ajout de fragments pour étendre la base de données horizontalement.

    ![MongoDB Scaling](https://www.mongodb.com/docs/manual/_images/scaled-sharded-cluster.png)

- **Cassandra**:
  - Cassandra est conçu pour une montée en charge horizontale sans goulot d’étranglement central. Vous pouvez ajouter des nœuds au cluster, et Cassandra redistribue automatiquement les données.
  - **Schéma**:
    - Nœuds ajoutés au cluster sans point de contrôle central.

    ![Cassandra Scaling](https://www.datastax.com/sites/default/files/styles/max_800x600/public/resources/images/blog/Cassandra-Scalable.jpg)

---

### 7. **Gestion du cache mémoire (schéma)**

- **MongoDB**:
  - MongoDB utilise le cache de **mappage mémoire**. Le moteur WiredTiger utilise la mémoire disponible pour les lectures, mais laisse au système d’exploitation la gestion précise de l’utilisation de la mémoire.
  - **Schéma**:
    - WiredTiger, mémoire tampon pour les écritures et mappage des pages de disque dans la mémoire.

    ![MongoDB Caching](https://docs.mongodb.com/manual/_images/wiredtiger-buf-mgr.bakedsvg.svg)

- **Cassandra**:
  - Cassandra utilise plusieurs caches, tels que le **key cache** (pour accélérer l'accès aux clés) et le **row cache** (pour stocker des lignes complètes en mémoire).
  - **Schéma**:
    - Cache pour les clés et les lignes pour accélérer les lectures fréquentes.

    ![Cassandra Cache](https://docs.datastax.com/en/cassandra-oss/3.0/cassandra/dml/img/cachingDiagram.png)

---

### Conclusion:
MongoDB et Cassandra sont tous deux de puissants moteurs NoSQL, mais leur utilisation dépend des cas d'usage. **MongoDB** est préférable pour les applications nécessitant une structure de document flexible, tandis que **Cassandra** excelle dans les environnements distribués nécessitant une haute disponibilité, une tolérance aux pannes et une gestion à grande échelle des écritures.


5) 

6) 

7) Pour mettre en œuvre les **mises à jour de données** (insert, update, delete) dans une base de données NoSQL comme **MongoDB** ou **Cassandra**, nous allons définir une classe Java pour chaque collection (MongoDB) ou table (Cassandra), qui permet d'effectuer les opérations CRUD (Create, Read, Update, Delete) pour un ou plusieurs enregistrements. Ensuite, nous allons effectuer des tests pour ces opérations.

### 1. **Exemple avec MongoDB**

MongoDB est orienté **document**, et ses collections sont des ensembles de documents JSON/BSON.

#### a. **Classe Java pour gérer les "Medecin" dans MongoDB**

```java
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class MedecinManagerMongo {
    private MongoCollection<Document> collection;

    public MedecinManagerMongo(MongoDatabase database) {
        this.collection = database.getCollection("Medecin");
    }

    // Insérer un enregistrement
    public void insertMedecin(Medecin medecin) {
        Document doc = new Document("numero_securite_sociale", medecin.getNumeroSecuriteSociale())
                          .append("nom", medecin.getNom())
                          .append("sexe", medecin.getSexe())
                          .append("date_naissance", medecin.getDateNaissance())
                          .append("specialite", medecin.getSpecialite())
                          .append("email", medecin.getEmail());
        collection.insertOne(doc);
    }

    // Insérer plusieurs enregistrements
    public void insertMultipleMedecins(List<Medecin> medecins) {
        List<Document> docs = medecins.stream().map(medecin -> new Document("numero_securite_sociale", medecin.getNumeroSecuriteSociale())
                                                          .append("nom", medecin.getNom())
                                                          .append("sexe", medecin.getSexe())
                                                          .append("date_naissance", medecin.getDateNaissance())
                                                          .append("specialite", medecin.getSpecialite())
                                                          .append("email", medecin.getEmail()))
                                       .toList();
        collection.insertMany(docs);
    }

    // Mettre à jour un enregistrement
    public void updateMedecin(String id, String newEmail) {
        collection.updateOne(new Document("_id", new ObjectId(id)),
                             new Document("$set", new Document("email", newEmail)));
    }

    // Supprimer un enregistrement
    public void deleteMedecin(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }
}
```

#### b. **Tests des opérations avec MongoDB**

```java
public class MongoDBTest {
    public static void main(String[] args) {
        // Connexion à la base MongoDB
        MongoDatabase database = new MongoClient("localhost", 27017).getDatabase("hospital");

        MedecinManagerMongo medecinManager = new MedecinManagerMongo(database);

        // Insérer un medecin
        Medecin medecin1 = new Medecin("12345678901", "Doe", "M", "12/05/1985", "Urologue", "john.doe@gmail.com");
        medecinManager.insertMedecin(medecin1);

        // Insérer plusieurs medecins
        List<Medecin> medecins = List.of(
            new Medecin("12345678902", "Smith", "F", "23/07/1987", "Pédiatre", "jane.smith@gmail.com"),
            new Medecin("12345678903", "Johnson", "M", "10/03/1975", "Cardiologue", "mike.johnson@gmail.com")
        );
        medecinManager.insertMultipleMedecins(medecins);

        // Mettre à jour un medecin
        medecinManager.updateMedecin("12345678901", "new.email@domain.com");

        // Supprimer un medecin
        medecinManager.deleteMedecin("12345678903");
    }
}
```

### 2. **Exemple avec Cassandra**

Cassandra est orienté **colonnes**, et nous devons adapter nos classes et méthodes à cette structure.

#### a. **Classe Java pour gérer les "Medecin" dans Cassandra**

```java
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;

import java.util.List;
import java.util.UUID;

public class MedecinManagerCassandra {
    private CqlSession session;

    public MedecinManagerCassandra(CqlSession session) {
        this.session = session;
    }

    // Insérer un enregistrement
    public void insertMedecin(Medecin medecin) {
        session.execute("INSERT INTO Medecin (id, numero_securite_sociale, nom, sexe, date_naissance, specialite, email) VALUES (?,?,?,?,?,?,?)",
                UUID.randomUUID(), medecin.getNumeroSecuriteSociale(), medecin.getNom(), medecin.getSexe(), medecin.getDateNaissance(), medecin.getSpecialite(), medecin.getEmail());
    }

    // Insérer plusieurs enregistrements
    public void insertMultipleMedecins(List<Medecin> medecins) {
        for (Medecin medecin : medecins) {
            insertMedecin(medecin);
        }
    }

    // Mettre à jour un enregistrement
    public void updateMedecin(UUID id, String newEmail) {
        session.execute("UPDATE Medecin SET email = ? WHERE id = ?", newEmail, id);
    }

    // Supprimer un enregistrement
    public void deleteMedecin(UUID id) {
        session.execute("DELETE FROM Medecin WHERE id = ?", id);
    }
}
```

#### b. **Tests des opérations avec Cassandra**

```java
import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraTest {
    public static void main(String[] args) {
        // Connexion à la base Cassandra
        try (CqlSession session = CqlSession.builder().withKeyspace("hospital").build()) {
            MedecinManagerCassandra medecinManager = new MedecinManagerCassandra(session);

            // Insérer un medecin
            Medecin medecin1 = new Medecin("12345678901", "Doe", "M", "12/05/1985", "Urologue", "john.doe@gmail.com");
            medecinManager.insertMedecin(medecin1);

            // Insérer plusieurs medecins
            List<Medecin> medecins = List.of(
                new Medecin("12345678902", "Smith", "F", "23/07/1987", "Pédiatre", "jane.smith@gmail.com"),
                new Medecin("12345678903", "Johnson", "M", "10/03/1975", "Cardiologue", "mike.johnson@gmail.com")
            );
            medecinManager.insertMultipleMedecins(medecins);

            // Mettre à jour un medecin
            medecinManager.updateMedecin(UUID.fromString("some-uuid-here"), "new.email@domain.com");

            // Supprimer un medecin
            medecinManager.deleteMedecin(UUID.fromString("some-uuid-here"));
        }
    }
}
```

### Conclusion

Ces exemples montrent comment gérer les mises à jour des données (insertion, modification, suppression) dans MongoDB et Cassandra en utilisant des classes Java dédiées. Les opérations CRUD sont illustrées pour un enregistrement ainsi que pour plusieurs enregistrements dans les deux bases NoSQL. Ces méthodes peuvent être facilement testées dans une application Java pour vérifier leur fonctionnement.

8) Pour effectuer l'interrogation et la manipulation des données avec MongoDB et Cassandra, nous allons définir une série de méthodes dans une classe Java pour chaque collection (MongoDB) ou table (Cassandra). Ces méthodes traiteront des données avec des opérations complexes telles que des groupements, des jointures, des pipelines d'agrégations, etc. Nous illustrerons également des tests pour chaque moteur NoSQL.

## 1. **Manipulation des données avec MongoDB**

MongoDB prend en charge les **pipelines d'agrégation**, qui permettent de traiter les documents par étapes. Nous allons définir une classe Java avec plusieurs méthodes pour manipuler les documents dans MongoDB.

### a. **Classe Java pour la collection "Medecin" dans MongoDB**

```java
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregation;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class MedecinManagerMongo {
    private MongoCollection<Document> collection;

    public MedecinManagerMongo(MongoDatabase database) {
        this.collection = database.getCollection("Medecin");
    }

    // 1. Rechercher tous les médecins ayant une spécialité donnée
    public List<Document> findMedecinsBySpecialite(String specialite) {
        return collection.find(Filters.eq("specialite", specialite)).into(new ArrayList<>());
    }

    // 2. Grouper les médecins par spécialité et compter le nombre de médecins par groupe
    public List<Document> groupMedecinsBySpecialite() {
        return collection.aggregate(
            Arrays.asList(
                Aggregates.group("$specialite", Accumulators.sum("count", 1))
            )
        ).into(new ArrayList<>());
    }

    // 3. Effectuer une jointure entre la collection "Medecin" et "Patient" pour afficher les rendez-vous
    public List<Document> joinMedecinWithPatients() {
        return collection.aggregate(
            Arrays.asList(
                Aggregates.lookup("Patient", "_id", "medecin", "list_rendez_vous")
            )
        ).into(new ArrayList<>());
    }

    // 4. Rechercher les médecins ayant plus de N consultations
    public List<Document> findMedecinsWithMoreThanNConsultations(int n) {
        return collection.find(Filters.size("list_consultations", n)).into(new ArrayList<>());
    }

    // 5. Grouper par ville et calculer le nombre total de médecins dans chaque ville
    public List<Document> groupMedecinsByCity() {
        return collection.aggregate(
            Arrays.asList(
                Aggregates.group("$adresse.ville", Accumulators.sum("count", 1))
            )
        ).into(new ArrayList<>());
    }

    // 6. Filtrer les médecins par sexe et trier par date de naissance
    public List<Document> filterMedecinsBySexAndSortByDate(String sexe) {
        return collection.find(Filters.eq("sexe", sexe))
                .sort(new Document("date_naissance", 1)) // 1 for ascending order
                .into(new ArrayList<>());
    }
}
```

### b. **Tests des méthodes MongoDB**

```java
public class MongoDBTest {
    public static void main(String[] args) {
        // Connexion à la base MongoDB
        MongoDatabase database = new MongoClient("localhost", 27017).getDatabase("hospital");

        MedecinManagerMongo medecinManager = new MedecinManagerMongo(database);

        // 1. Rechercher tous les médecins ayant la spécialité "Cardiologue"
        List<Document> cardiologues = medecinManager.findMedecinsBySpecialite("Cardiologue");
        cardiologues.forEach(System.out::println);

        // 2. Grouper les médecins par spécialité
        List<Document> groupedBySpecialite = medecinManager.groupMedecinsBySpecialite();
        groupedBySpecialite.forEach(System.out::println);

        // 3. Jointure avec la collection "Patient" pour afficher les rendez-vous
        List<Document> medecinPatients = medecinManager.joinMedecinWithPatients();
        medecinPatients.forEach(System.out::println);

        // 4. Rechercher les médecins ayant plus de 10 consultations
        List<Document> busyMedecins = medecinManager.findMedecinsWithMoreThanNConsultations(10);
        busyMedecins.forEach(System.out::println);

        // 5. Grouper les médecins par ville
        List<Document> groupedByCity = medecinManager.groupMedecinsByCity();
        groupedByCity.forEach(System.out::println);

        // 6. Filtrer les médecins de sexe "M" et trier par date de naissance
        List<Document> maleMedecins = medecinManager.filterMedecinsBySexAndSortByDate("M");
        maleMedecins.forEach(System.out::println);
    }
}
```

## 2. **Manipulation des données avec Cassandra**

Cassandra utilise le langage **CQL (Cassandra Query Language)** et fonctionne avec des **tables à colonnes**. Nous allons créer des méthodes Java pour effectuer des requêtes complexes sur les données dans Cassandra.

### a. **Classe Java pour la table "Medecin" dans Cassandra**

```java
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;

import java.util.List;
import java.util.UUID;

public class MedecinManagerCassandra {
    private CqlSession session;

    public MedecinManagerCassandra(CqlSession session) {
        this.session = session;
    }

    // 1. Rechercher tous les médecins ayant une spécialité donnée
    public ResultSet findMedecinsBySpecialite(String specialite) {
        return session.execute("SELECT * FROM Medecin WHERE specialite = ?", specialite);
    }

    // 2. Grouper les médecins par ville et compter
    public ResultSet groupMedecinsByCity() {
        return session.execute("SELECT ville, COUNT(*) FROM Medecin GROUP BY ville");
    }

    // 3. Rechercher les médecins ayant plus de N consultations (hypothèse : consultations dans une table séparée)
    public ResultSet findMedecinsWithMoreThanNConsultations(int n) {
        return session.execute("SELECT * FROM Medecin WHERE consultations_count > ?", n);
    }

    // 4. Rechercher les rendez-vous d'un médecin par son ID
    public ResultSet findRendezVousByMedecin(UUID medecinId) {
        return session.execute("SELECT * FROM RendezVous WHERE medecin_id = ?", medecinId);
    }

    // 5. Filtrer les médecins par sexe et trier par date de naissance
    public ResultSet filterMedecinsBySexAndSortByDate(String sexe) {
        return session.execute("SELECT * FROM Medecin WHERE sexe = ? ORDER BY date_naissance ASC", sexe);
    }
}
```

### b. **Tests des méthodes Cassandra**

```java
import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraTest {
    public static void main(String[] args) {
        // Connexion à la base Cassandra
        try (CqlSession session = CqlSession.builder().withKeyspace("hospital").build()) {
            MedecinManagerCassandra medecinManager = new MedecinManagerCassandra(session);

            // 1. Rechercher tous les médecins ayant la spécialité "Cardiologue"
            ResultSet cardiologues = medecinManager.findMedecinsBySpecialite("Cardiologue");
            cardiologues.forEach(System.out::println);

            // 2. Grouper les médecins par ville
            ResultSet groupedByCity = medecinManager.groupMedecinsByCity();
            groupedByCity.forEach(System.out::println);

            // 3. Rechercher les médecins ayant plus de 10 consultations
            ResultSet busyMedecins = medecinManager.findMedecinsWithMoreThanNConsultations(10);
            busyMedecins.forEach(System.out::println);

            // 4. Rechercher les rendez-vous d'un médecin
            ResultSet rendezVous = medecinManager.findRendezVousByMedecin(UUID.randomUUID());
            rendezVous.forEach(System.out::println);

            // 5. Filtrer les médecins de sexe "M" et trier par date de naissance
            ResultSet maleMedecins = medecinManager.filterMedecinsBySexAndSortByDate("M");
            maleMedecins.forEach(System.out::println);
        }
    }
}
```

## Conclusion

Ces exemples montrent comment effectuer des opérations complexes telles que des groupements, des jointures et des filtres dans MongoDB et Cassandra. Nous avons défini plusieurs méthodes Java pour manipuler les données dans chaque moteur NoSQL, et des tests ont été mis en place pour vérifier leur bon fonctionnement. Ces opérations peuvent être adaptées à des besoins spécifiques selon la structure de vos données et les impératifs de votre application.