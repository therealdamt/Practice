# Practice
Mongo Practice Core, it's almost completed, the features below are the remaining to add:

* Spawn Items
* Specatotor Items
* Complete Party System
* 2v2/Party Duels

- The list above will be updated as the project is getting updated.

***

### Intorduction

Hello everyone, my name is waseem and today I present to you a fully custom practice core that has one depenency which is Mongo Database

### Infromation

* Version | 1.7-1.8
* Configurable
* Almost Completed
* Optimized

### API

* The API is still not released, once the project is completed, an API will be added along with a wiki.

### Configuration

```yml
other:
  join-message:
    - "&7&m---------------------------------"
    - "              &b&lPractice"
    - " "
    - "&b&lWelcome &7to &b&lPractice"
    - "&7This core was created by &b&ldamt"
    - " "
    - "&b&l* &7To duel a specific user do &b&l/duel"
    - "&b&l* &7To queue a match&b&l, click on the sword!"
    - "&7&m---------------------------------"

settings:
  use-placeholder: true
  spawn-world: "world"
  required-wins: 10
  pearl-cooldown: 16
  coinshop-command: "/coinshop"

mongo:
  host: "localhost"
  port: 27017
  auth:
    enabled: false
    database: "admin"
    username: ""
    password: ""

scoreboard:
  title: "&b&lPractice"
  normal:
    - "&7&m--------------------"
    - "&b&lOnline&7: {online}"
    - "&b&lPlaying&7: {queue}"
    - " "
    - "&7damt.xyz"
    - "&7&m--------------------"
  in-game:
    - "&7&m--------------------"
    - "&b&lOpponent&7: {opponent}"
    - " "
    - "&b&lYour Ping&7: {player_ping}"
    - "&b&lTheir Ping&7: {opponent_ping}"
    - " "
    - "&7damt.xyz"
    - "&7&m--------------------"
  enderpearl-cooldown:
    - "&7&m--------------------"
    - "&b&lEnderpearl&7: {time}"
    - " "
    - "&b&lYour Ping&7: {player_ping}"
    - "&b&lTheir Ping&7: {opponent_ping}"
    - " "
    - "&7damt.xyz"
    - "&7&m--------------------"
```

### Contact
If you'd like to contact me, you can use any of the 3 platforms below:

* Discord - bingbongwaseem
