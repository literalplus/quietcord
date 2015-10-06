# QuietCord [![Build Status](https://ci.nowak-at.net/job/public~quietcord/badge/icon)](https://ci.nowak-at.net/job/public~quietcord)
A simple BungeeCord plugin which aims to reduce log spam caused by InitialHandler.

# User Story
Don't you know this problem?

You want to find some useful information in your BungeeCord log and are too lazy to
set up a custom log for your plugin?

You open an issue with BungeeCord, but [nothing happens](https://github.com/SpigotMC/BungeeCord/pull/1484)??

You end up endlessly scrolling through your logs because `grep` is too hard for you
and your dumb server owner doesn't allow you to Install Gentoo™?

Are you sick of your [BungeeCord](https://github.com/SpigotMC/BungeeCord) logs looking like this?

![a log with a lot of InitialHandler spam](https://github.com/xxyy/quietcord/raw/master/screenshots/initialhandler-spam.png)

Well now there's a solution!

**Introducing: QuietCord** (todo: inspirational image)

# Features

 * Blocks these useless `InitialHandler has connected` messages
 * configurable
 * Probably doesn't impact performance too much!
 * has unit tests!!
 * Gradle-based!!! (super hipster!!)
 * no nms!!
 * no ProtocolLib!!
 * web-scale!!! (that's a lie)
 * has cakes!!!!!!
 * Chuck Norris would probably approve!!!!

With this, your logs can look like this again:

![a clean log!!!!](https://github.com/xxyy/quietcord/raw/master/screenshots/clean-log.png)

Isn't that the dream of every singl BungeeCord admin out there? Yes? No?? Then this plugin is the right tool for you!
 
# Installation

Get official releases at [SpigotMC](https://www.spigotmc.org/resources/quietcord.12940/).

Download the latest binaries from [my Continous Integration server](https://ci.nowak-at.net/job/public~quietcord/). Note that these are development builds and may not be stable for use in a Production environment.
 
If you are afraid that the binaries provided are malicious, are passionate about your freedom or just like compiling things, you can also compile from source. Since Gradle is cross-platform, you can run builds on a lot of platforms. If you happen to use Linux, try the following: (If not, you should definitely reconsider your OS choice)
  
````bash
git clone git@github.com:xxyy/quietcord.git
cd quietcord
./gradlew
````

(If you love being controlled by monopolies, you can also compile this on your Microsoft® Windows®-based computer. Further instructions are not detailed here because of ethical reasons. (`gradlew.bat`))
 
# Configuration

Look at [`plugins/QuietCord/config.yml`](https://github.com/xxyy/quietcord/blob/master/src/main/resources/config.default.yml). If you don't understand that, it has information
at the top on how to get help.

# License

This project is licensed under the MIT license. 
[Please refer to the LICENSE file for details.](https://github.com/xxyy/quietcord/blob/master/LICENSE)
