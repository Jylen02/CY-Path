# CY-Path
#
# Pour exécuter le livrable :
#
# 	- Télécharger le fichier "runnableBoard.jar" et placez le dans le dossier de votre choix
# 	- Ouvrir le terminal sur Windows
# 	- Taper la commande "cd CheminAbsolu" en remplaçant CheminAbsolu par le chemin absolu du fichier runnableBoard.jar
# 	- Vérifier que votre version de Java est à jour, sinon télécharger la dernière version de jdk sur le site officiel d'Oracle et relancer la console.
# 	- Taper ensuite la commande "java -jar runnableBoard.jar"
#
# Fonctionnalités implémentées :
# 	- Choix du nombre de joueurs (2 ou 4 joueurs)
# 	- Initialisation et affichage du plateau
# 	- Les joueurs jouent chacun leur tour
# 	- Choix de l'action 
#		-> Déplacement du pion
#			-> Choix du mouvement à effectuer
# 			-> Vérification des mouvements (les normaux, le saut et les diagonales) possibles et affichage de ces derniers
# 			-> Vérifier si le pion a traversé le plateau (donc s'il a gagné)
#		-> Poser un mur
#			-> Choix de son orientation et de sa position
#			-> Vérifier que le mur ne dépasse pas du plateau
#			-> Vérifier qu'il n'y a pas de superposition avec un autre mur
#			-> Impossiblité de poser un mur sur une bordure du plateau
#
# Fonctionnalités non implémentées :
# 	- Vérification qu'il y a maximun 20 murs sur le plateau
# 	- DFS à appliquer à tous les joueurs qui vérifie si tous les joueurs ont la possibilité de gagner
#	- Interface homme-machine
#
# Description de l'affichage du plateau :
#	- Les joueurs sont indiqués par leur numéro
#	- Les barres verticales "|" et horizontales "-" représentent la grille du plateau
#	- Les plus "+" représentent l'intersection entre les lignes et les colonnes de la grille
#	- Les murs sont représentés par des slash "/"
#	- Les coordonnées sont indiquées en haut et à gauche du plateau
#
# Comment jouer ? :
#	- Renseigner le nombre de joueurs (2 ou 4)
#	- Tour d'un joueur
#		-> Une liste des déplacements possibles pour le joueur actif est proposée
#		-> Choisir une action entre "avancer le pion" ou "poser un mur"
#		-> Pour avancer le pion :
#			-> Renseigner les coordonnées de la destination du pion 
#		-> Pour le mur :
#			-> Renseigner les coordonnées du milieu du mur qui correspond à une case "+"
#			-> Renseigner l'orientation du mur
#	- C'est maintenant au tour du joueur suivant
#	- La partie se termine quand l'un des joueurs a atteint le côté opposé de son point de départ
