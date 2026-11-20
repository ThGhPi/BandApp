# PLAN DE TEST – BANDAPP

**Projet :** BandApp – Application de gestion d'un orchestre associatif  
**Version du document :** 0.1 – Première ébauche  
**Statut :** Document de travail  
**Responsable des tests :** Développeur du projet -> Thomas Gheerbrant 
**Environnement technique :** Java 21, Spring Boot 3.5.x, Angular 20, PostgreSQL 18

---

# 1. Présentation et objectifs

## 1.1. Contexte du projet

BandApp est une application web responsive destinée à faciliter la gestion quotidienne d'un orchestre associatif.

Le projet répond aux difficultés rencontrées dans l'organisation de la Banda Adagio, notamment la dispersion des informations, le suivi des présences, la gestion des partitions et la coordination des événements.

L'application repose sur une architecture organisée en couches, comprenant :

- Une interface utilisateur développée avec Angular.
- Une API REST développée avec Java et Spring Boot.
- Une couche métier assurant l'application des règles de gestion.
- Une couche d'accès aux données utilisant Spring Data JPA et Hibernate.
- Une base de données relationnelle PostgreSQL.

L'application intègre également des mécanismes d'authentification et d'autorisation basés sur Spring Security et les JSON Web Tokens (JWT).

## 1.2. Objectifs du plan de test

Le présent document définit l'organisation et les modalités de validation de l'application BandApp.

Les objectifs sont les suivants :

- Vérifier la conformité des fonctionnalités avec les besoins exprimés dans le cahier des charges.
- Vérifier le bon fonctionnement des composants logiciels.
- Garantir la cohérence des échanges entre le frontend, le backend et la base de données.
- Vérifier la sécurité des mécanismes d'authentification et d'autorisation.
- Identifier les anomalies et limiter les régressions lors des évolutions.
- Valider l'application du point de vue des différents utilisateurs.

## 1.3. Références documentaires

Les documents utilisés comme références sont :

- Étude de concept BandApp.
- Cahier des charges BandApp.
- Rapport de projet BandApp.
- MCD, MLD et LDD 
- Diagrammes UML.
- Documentation technique du projet.

Les spécifications fonctionnelles du cahier des charges constituent la référence principale pour définir les résultats attendus.

---

# 2. Périmètre des tests

## 2.1. Fonctionnalités incluses

Les tests portent prioritairement sur les modules fonctionnels suivants.

| Module | Fonctionnalités concernées |
|---|---|
| Authentification | Inscription, connexion, déconnexion, profil, gestion des accès |
| Utilisateurs | Gestion des utilisateurs, gestion des rôles |
| Événements | Gestion des évènements et programme musicaux |
| Présences | Réponse aux sondages de présence et modification des réponses |
| Sondages | Création, consultation, vote et clôture |
| Partitions | Ajout, consultation, téléchargement, gestion des versions et archivage |
| Pupitres / groupes de travail | Création, modification et gestion des membres |
| Lieux | Enregistrement et consultation des lieux |
| Factures | Génération des documents PDF prévus dans le périmètre |

## 2.2. Fonctionnalités exclues ou secondaires

Les fonctionnalités suivantes sont considérées comme secondaires ou hors périmètre prioritaire :

- Organisation du covoiturage,
- Gestion du transport du matériel,
- Boîte à idée,
- Stockage des photographies.

Elles ne seront intégrées au plan de validation que si leur développement est effectivement retenu.

## 2.3. Éléments techniques concernés

Les tests concernent les différentes couches de l'application :

- Frontend Angular : composants, services, formulaires, gestion de l'authentification et navigation.
- Backend Spring Boot : contrôleurs REST, services métier, repositories, DTO et sécurité.
- Base de données PostgreSQL : intégrité des données, relations et contraintes.
- Communication frontend/backend : échanges HTTP, gestion des erreurs et authentification.

---

# 3. Stratégie et organisation des tests

La stratégie de test repose sur quatre catégories complémentaires.

| Catégorie | Objectif | Niveau |
|---|---|---|
| Tests unitaires | Vérifier le comportement des composants isolés | Composant |
| Tests d'intégration | Vérifier les interactions entre plusieurs composants | Technique |
| Tests de sécurité | Vérifier la protection des ressources et des données | Transversal |
| Tests d'acceptation | Vérifier la conformité aux besoins utilisateurs | Fonctionnel |

## 3.1. Priorisation

Les tests sont priorisés selon les critères suivants :

- Criticité fonctionnelle : importance de la fonctionnalité dans le fonctionnement de l'application.
- Criticité métier : conséquences d'une anomalie sur l'activité de l'association.
- Criticité de sécurité : conséquences potentielles d'un accès non autorisé ou d'une fuite de données.

Les fonctionnalités d'authentification, d'autorisation, de gestion des utilisateurs et de persistance des données sont considérées comme prioritaires.

## 3.2. Ordre d'exécution

Les tests sont réalisés selon l'organisation suivante :

1. Tests unitaires lors du développement des composants.
2. Tests d'intégration lors de la validation des interactions entre couches.
3. Tests de sécurité lors de la validation des mécanismes de protection.
4. Tests d'acceptation lors de la validation des fonctionnalités complètes.

Des tests de non-régression sont également exécutés après toute modification susceptible d'affecter une fonctionnalité déjà validée.

---

# 4. Environnement et outils de test

## 4.1. Environnement technique

| Élément | Technologie |
|---|---|
| Langage backend | Java 21 |
| Framework backend | Spring Boot 3.5.x |
| Langage frontend | TypeScript |
| Framework frontend | Angular 20 |
| Base de données | PostgreSQL |
| Accès aux données | Spring Data JPA / Hibernate |
| Sécurité | Spring Security / JWT |
| Mapping | MapStruct |
| Conteneurisation | Docker |
| Gestion de versions | Git / GitHub |

## 4.2. Outils de test

### Backend

- JUnit 5 : exécution et organisation des tests.
- Mockito : simulation des dépendances.
- AssertJ : vérification des résultats.
- Spring Boot Test : chargement du contexte applicatif.
- MockMvc : simulation des requêtes HTTP vers les contrôleurs.
- Spring Security Test : validation des mécanismes de sécurité.
- Testcontainers : utilisation d'une base PostgreSQL conteneurisée pour les tests d'intégration, lorsque retenu.

### Frontend

- Jasmine : framework de description et d'exécution des tests unitaires.
- Karma : environnement d'exécution des tests dans un navigateur, si cette configuration est conservée.
- Angular Testing Utilities : outils de test des composants et services Angular.
- HttpTestingController : simulation et vérification des requêtes HTTP.

### Outils complémentaires

- Docker : reproduction d'un environnement technique isolé.
- SonarQube : analyse statique et qualité du code. **Non opérationnel actuellement**.
- Jenkins : automatisation de l'exécution des tests dans une chaîne d'intégration continue. **Non opérationnel actuellement**.

---

# 5. Organisation des tests unitaires

## 5.1. Objectifs

Les tests unitaires ont pour objectif de vérifier le comportement individuel des composants, indépendamment de leurs dépendances externes.

Ils concernent principalement :

- Les services métier.
- Les composants d'accès aux données lorsque cela est pertinent.
- Les fonctions utilitaires.
- Les composants et services Angular.

Les dépendances sont simulées lorsque cela permet d'isoler le composant testé.

## 5.2. Backend

Les tests unitaires backend sont réalisés avec JUnit 5, Mockito et AssertJ.

Les principales classes concernées sont :

| Composant | Vérifications prévues |
|---|---|
| UserService | Création d'utilisateur, modification du profil, gestion des rôles, encodage du mot de passe |
| SurveyService | Création d'un sondage, ajout et suppression d'un vote, calcul des résultats |
| EventService | Création, modification et suppression d'un événement |
| SectionService | Création, modification et suppression d'un pupitre |
| ScoreService | Gestion des partitions et de leurs versions |
| Mapper MapStruct | Transformation des entités en DTO et inversement |

Exemples de cas de test :

**TU-BACK-001 – Création d'un utilisateur**

- Précondition : les données utilisateur sont valides.
- Action : appeler le service de création.
- Résultat attendu : un utilisateur est créé avec les informations fournies et un mot de passe encodé.

**TU-BACK-002 – Création d'un utilisateur avec une adresse électronique déjà utilisée**

- Précondition : un utilisateur possède déjà cette adresse électronique.
- Action : tenter de créer un second utilisateur avec la même adresse.
- Résultat attendu : l'opération est refusée conformément à la règle d'unicité.

**TU-BACK-003 – Ajout d'un vote**

- Précondition : le sondage est ouvert et l'utilisateur est autorisé à voter.
- Action : enregistrer un choix.
- Résultat attendu : le vote est correctement enregistré et associé au sondage et à l'utilisateur.

**TU-BACK-004 – Vote sur un sondage clôturé**

- Précondition : le sondage est clôturé.
- Action : tenter d'ajouter un vote.
- Résultat attendu : une erreur métier est retournée et aucune modification n'est effectuée.

**TU-BACK-005 – Conversion Entity / DTO**

- Précondition : une entité valide est disponible.
- Action : effectuer le mapping vers le DTO correspondant.
- Résultat attendu : les attributs attendus sont correctement transférés et les données non exposées ne sont pas présentes.

## 5.3. Frontend (non implémenté actuellement)

Les tests unitaires Angular portent notamment sur :

- Les composants de présentation.
- Les formulaires et leurs validateurs.
- Les services de communication avec l'API.
- Les services d'authentification.
- Les intercepteurs HTTP.
- Les guards de navigation.

Exemples :

**TU-FRONT-001 – Validation du formulaire de connexion**

- Précondition : le formulaire est initialisé.
- Action : saisir des identifiants valides.
- Résultat attendu : le formulaire est valide.

**TU-FRONT-002 – Intercepteur HTTP**

- Précondition : un utilisateur possède un jeton d'authentification valide.
- Action : effectuer une requête HTTP nécessitant une authentification.
- Résultat attendu : l'en-tête Authorization contient le schéma Bearer et le jeton attendu, conformément au mode d'authentification retenu.

**TU-FRONT-003 – AuthGuard**

- Précondition : aucun utilisateur n'est authentifié.
- Action : tenter d'accéder à une route protégée.
- Résultat attendu : l'accès est refusé et l'utilisateur est redirigé vers la connexion.

---

# 6. Organisation des tests d'intégration

## 6.1. Objectifs

Les tests d'intégration ont pour objectif de vérifier le bon fonctionnement des interactions entre les différentes couches de l'application.

Ils permettent notamment de vérifier :

- La communication entre les contrôleurs et les services.
- La sérialisation et la désérialisation des données JSON.
- Le comportement des endpoints REST.
- Les interactions entre les services et les repositories.
- La persistance des données dans PostgreSQL.
- L'application effective des règles de sécurité.

## 6.2. Tests d'intégration backend

Les tests utilisent principalement Spring Boot Test et MockMvc.

Lorsque cela est nécessaire, Testcontainers permet de reproduire un environnement PostgreSQL proche de celui utilisé par l'application.

| ID | Scénario | Résultat attendu |
|---|---|---|
| TI-BACK-001 | Connexion avec des identifiants valides | Réponse conforme et émission du jeton d'authentification |
| TI-BACK-002 | Connexion avec un mot de passe incorrect | Réponse HTTP 401 |
| TI-BACK-003 | Consultation des utilisateurs authentifiés | Retour des données autorisées |
| TI-BACK-004 | Création d'un événement | Événement enregistré en base |
| TI-BACK-005 | Modification d'un événement | Données modifiées et persistées |
| TI-BACK-006 | Création d'un sondage | Sondage et choix associés enregistrés |
| TI-BACK-007 | Ajout d'un vote | Vote enregistré et résultats cohérents |
| TI-BACK-008 | Consultation des partitions | Retour des partitions accessibles à l'utilisateur |
| TI-BACK-009 | Suppression d'une entité liée à d'autres données | Respect des règles d'intégrité référentielle |

## 6.3. Tests d'intégration frontend/backend (non implémenté actuellement)

Ces tests vérifient les échanges entre Angular et l'API REST.

Les scénarios prioritaires sont :

- Connexion depuis le formulaire Angular.
- Transmission du jeton d'authentification.
- Accès aux routes protégées.
- Récupération des événements.
- Création et consultation des sondages.
- Enregistrement d'une réponse à un sondage.
- Gestion des erreurs retournées par l'API.

---

# 7. Organisation des tests de sécurité (incomplet / méthodes de test à revoir)

## 7.1. Objectifs

Les tests de sécurité doivent vérifier que les ressources de l'application sont accessibles uniquement aux utilisateurs autorisés.

Ils portent principalement sur :

- L'authentification.
- L'autorisation selon les rôles.
- La protection des endpoints REST.
- La gestion des jetons JWT.
- La protection des données personnelles.
- La validation des données reçues par l'API.
- La sécurité des fichiers transmis et téléchargés.

## 7.2. Authentification

| ID | Scénario | Résultat attendu |
|---|---|---|
| TS-001 | Accès à une route protégée sans jeton | HTTP 401 |
| TS-002 | Accès avec un jeton invalide | HTTP 401 |
| TS-003 | Accès avec un jeton expiré | HTTP 401 |
| TS-004 | Connexion avec des identifiants incorrects | HTTP 401 |
| TS-005 | Connexion avec des identifiants valides | Authentification réussie |

## 7.3. Autorisation

Les rôles principaux sont :

- ADMIN : administrateur.
- ORGANIZER : organisateur.
- ARRANGER : arrangeur.
- MEMBER : musicien ou membre.

Les contrôles doivent notamment vérifier :

- Un membre ne peut pas gérer les comptes utilisateurs.
- Un arrangeur peut gérer les partitions conformément à ses permissions.
- Un organisateur peut créer et modifier les événements autorisés.
- Un membre ne peut pas modifier les données réservées aux administrateurs.
- Un utilisateur ne peut pas consulter ou modifier des informations auxquelles son rôle ne lui donne pas accès.

Les contrôles doivent être réalisés directement sur les endpoints backend et ne doivent pas reposer uniquement sur les protections Angular.

## 7.4. Protection des données

Les vérifications portent également sur :

- L'encodage des mots de passe.
- L'absence de transmission des mots de passe dans les réponses de l'API.
- La validation des données reçues.
- La protection contre les accès non autorisés aux fichiers.
- La limitation des types et tailles de fichiers acceptés.
- La gestion des erreurs sans divulgation d'informations sensibles.

---

# 8. Organisation des tests d'acceptation

## 8.1. Objectifs

Les tests d'acceptation permettent de vérifier que l'application répond aux besoins fonctionnels exprimés dans le cahier des charges.

Contrairement aux tests unitaires et d'intégration, ils sont réalisés du point de vue de l'utilisateur.

Ils concernent les principaux parcours métier de BandApp.

## 8.2. Scénarios d'acceptation

### TA-001 – Connexion d'un musicien

**Acteur :** Musicien.

**Préconditions :**
- Le compte utilisateur existe.
- Le compte est actif.

**Déroulement :**
1. L'utilisateur accède à la page de connexion.
2. Il saisit son nom d'utilisateur et son mot de passe.
3. Il valide le formulaire.

**Résultat attendu :**
- L'authentification est réussie.
- L'utilisateur accède à son espace personnel.
- Les fonctionnalités accessibles correspondent à son rôle.

### TA-002 – Consultation des événements

**Acteur :** Musicien.

**Déroulement :**
1. L'utilisateur accède au calendrier.
2. Il consulte les événements à venir.
3. Il sélectionne un événement.

**Résultat attendu :**
- Les événements sont affichés chronologiquement.
- Les informations principales sont disponibles.
- Le détail de l'événement est accessible.

### TA-003 – Réponse à un sondage de présence

**Acteur :** Musicien.

**Déroulement :**
1. L'utilisateur consulte un événement.
2. Il sélectionne Présent, Peut-être ou Absent.
3. Il valide sa réponse.

**Résultat attendu :**
- La réponse est enregistrée.
- L'état de sa participation est actualisé.
- L'organisateur peut consulter les résultats.

### TA-004 – Création d'un événement

**Acteur :** Organisateur.

**Déroulement :**
1. L'organisateur accède au formulaire de création.
2. Il renseigne le titre, le type, la date, l'heure et le lieu.
3. Il valide le formulaire.

**Résultat attendu :**
- L'événement est créé.
- Les informations sont enregistrées.
- L'événement est consultable dans le calendrier.

### TA-005 – Gestion des partitions

**Acteur :** Arrangeur.

**Déroulement :**
1. L'arrangeur accède à la gestion des partitions.
2. Il sélectionne un fichier et renseigne les métadonnées.
3. Il associe la partition à un ou plusieurs pupitres.
4. Il valide l'ajout.

**Résultat attendu :**
- Le fichier est enregistré.
- Les métadonnées sont disponibles.
- La partition est accessible aux utilisateurs autorisés.

### TA-006 – Création et participation à un sondage

**Acteur :** Administrateur puis musicien.

**Déroulement :**
1. L'administrateur crée un sondage.
2. Il définit la question et les choix proposés.
3. Le musicien consulte le sondage.
4. Il sélectionne une réponse.

**Résultat attendu :**
- Le sondage est accessible.
- Le vote est enregistré.
- Les résultats sont actualisés.
- Un sondage clôturé ne permet plus de voter.

### TA-007 – Gestion des pupitres

**Acteur :** Administrateur.

**Déroulement :**
1. L'administrateur accède à la gestion des pupitres.
2. Il crée un pupitre.
3. Il lui associe des membres.
4. Il consulte la liste des membres.

**Résultat attendu :**
- Le pupitre est créé.
- Les associations entre membres et pupitre sont correctement enregistrées.
- Les informations sont consultables.

---

# 9. Gestion des anomalies et critères de validation

## 9.1. Gestion des anomalies

Toute anomalie identifiée pendant les tests doit être documentée.

Les informations minimales à conserver sont :

- Identifiant de l'anomalie.
- Date de détection.
- Fonctionnalité concernée.
- Description du problème.
- Étapes permettant de reproduire le problème.
- Résultat obtenu.
- Résultat attendu.
- Niveau de criticité.
- Statut de traitement.

Les anomalies sont classées selon trois niveaux :

| Niveau | Description |
|---|---|
| Critique | Empêche une fonctionnalité essentielle ou compromet la sécurité des données |
| Majeur | Affecte significativement le fonctionnement d'une fonctionnalité |
| Mineur | Défaut limité sans impact majeur sur le fonctionnement général |

Statuts possibles : À traiter, En cours, Corrigée, À retester, Validée.

## 9.2. Critères de validation

Une fonctionnalité est considérée comme validée lorsque :

- Les tests associés ont été exécutés.
- Les résultats obtenus correspondent aux résultats attendus.
- Aucune anomalie critique ou majeure non résolue ne subsiste sur la fonctionnalité.
- Les contrôles de sécurité applicables sont satisfaisants.
- Les éventuels tests de non-régression sont concluants.

## 9.3. Bilan des tests

À l'issue de chaque campagne de tests, un bilan est établi.

Il comprend :

- Le nombre de tests prévus.
- Le nombre de tests exécutés.
- Le nombre de tests réussis.
- Le nombre de tests échoués.
- Le nombre de tests non exécutés.
- Les anomalies découvertes.
- Les anomalies corrigées.
- Les fonctionnalités validées.

Ce bilan permet de suivre l'état général de la qualité de l'application et de justifier sa validation avant déploiement.

---

**Fin du document – Plan de test BandApp, version 0.1**