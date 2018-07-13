# RubiksSolveur

![#1589F0](https://placehold.it/15/1589F0/000000?text=+) `Merci de lire la doc/compte rendu disponible ici : `

  - https://docs.google.com/document/d/1FFM5r81TFRC8KPaKa_8uvMrZvo2xCX05Hk2Mys3U3oY/edit?usp=sharing

:warning: Merci de regarder le repo de l'application Android 

  - [Voir le repo Android Rubiks](https://github.com/TimPrd/Android-Rubiks)
  
  - [Voir la video de démo](https://youtu.be/MeojRx8Fa_A)
  
  Explication : 
  Cette vidéo permet de voir la résolution en action. 
  Rentrer le mot "Flamingo" permet d'avoir un cube déjà instancié (plus rapide et pratique pour la vidéo) ressemblant à celui-ci : 
  [[https://github.com/TimPrd/-Java-RubiksSolveur/ruwix.png|alt=rubik]]

  On appuie alors sur résoudre, le serveur reçoit le cube, demande a des workers de trouver une solution et restitue le résultat.
  Il est ensuite interprété et mis en images par le client.
  
  Afin de tester, il faut :
  - Lancer le serveur. (Network/Main.java)
  
  - Lancer quelques workers qui travailleront pour le serveur (Network/Worker/ClientWorker.java)
  
  - Installer l'application (https://github.com/TimPrd/Android-Rubiks), rentrer l'adresse du serveur, le cube et appuyer sur résoudre
  
# I. Introduction

Dans le cadre de notre projet de fin d’année en Java au sein de Sorbonne Université campus UPMC, nous avons développé un projet en binôme.
Ce projet nous permettait de mettre en exécution les différents préceptes vu en cours, pour les adapter au besoin d’une application avec un réel but.
Nos professeurs nous assiste dans la réalisation de ce projet en nous guidant et en nous suggérant des manières d’aborder la problématique.

# II. But de l’application

Le but de notre application était de résoudre un rubik’s cube. Ce fameux cube contenant 43 252 003 274 489 856 000 possibilités dans sa version 3x3x3 la plus classique.   

## II. A Version minimale 

La version minimale du projet se limite à la résolution totale d’un cube déjà mélangé. Notre projet est inspiré de la version web du solveur de rubik’s cube : 
Ruwix (https://ruwix.com/online-rubiks-cube-solver-program/).
Ce projet, complexe, nous a demandé de bien réfléchir à la méthode la plus simple pour résoudre le cube parmi plusieurs choix tel que : La résolution par étapes «humaines», 
la résolution par méthodes mathématiques : Kociemba, nombre de Dieu etc.. ou par brute-force en prenant par la suite le meilleur parcours parmi tous les parcours possible.

## II. B Version avancée
La version avancée consiste à représenter un cube via une application mobile qui permettra de récupérer directement les différentes couleurs du cube mélangé. Cette application doit communiquer avec un serveur qui calcule la résolution pour le client (l’application).
