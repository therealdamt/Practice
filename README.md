# Practice Core
A practice core made with the Mongo database.

***

### Intorduction

Hello! My name is damt, today I present to you a fully custom practice core that has only one depenency which is access to a Mongo database.

### Infromation

* Supported Version(s) 1.7.X - 1.8.X
* Configurable
* Optimized

### API

* None currently, will be implemented soon.

### Configuration

```yml
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
    - "&b&lPlayer Name&7: {player}"
    - " "
    - "&b&lWins&7: {wins}"
    - "&b&lLoses&7: {loses}"
    - "&b&lCoins&7: {coins}"
    - " "
    - "&b&lPlayers Online&7: {online}"
    - "&b&lPlayers In Queue &7: {queue}"
    - "&7&m--------------------"
  in-game:
    - "&7&m--------------------"
    - "&b&lYou&7: {player} &b({player_ping})"
    - "&b&lOpponent&7: {opponent} &b({opponent_ping}"
    - " "
    - "&b&lKit&7: {kit}"
    - "&7&m--------------------"

```

### Contact
If you'd like to contact me, you can use any of the 3 platforms below:

* My Discord - damt#0866
* [My Telegram](https://t.me/therealdamt)
* [My Webiste](https://damt.xyz)
