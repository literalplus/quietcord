# QuietCord
A simple BungeeCord plugin which aims to reduce log spam caused by InitialHandler.

# User Story
Don't you know this problem?

You want to find some useful information in your BungeeCord log and are too lazy to
set up a custom log for your plugin?

You open an issue with BungeeCord, but [nothing happens](https://github.com/SpigotMC/BungeeCord/pull/1484)??

You end up endlessly scrolling through your logs because `grep` is too hard for you
and installing Gentoo is not an option?

Well now there's a solution!

**Introducing: QuietCord** (todo: inspirational image)

# Features

 * Blocks these useless InitialHandler has connected messages
 * configurable
 * Probably doesn't impact performance too much!
 * has unit tests!!
 * Gradle-based!!! (super hipster!!)
 * no nms!!
 * no ProtocolLib!!
 * web-scale!!! (that's a lie)
 * has cakes!!!!!!
 
# Installation
 
Currently, you'll have to build this yourself. Get a Linux box running, or if you
 really have to, use Cygwin. Execute:
  
````bash
git clone git@github.com:xxyy/quietcord.git
cd quietcord
./gradlew
````

(Alternatively, you can use `gradlew.bat` on Windows, but that's not supported.
 Install Gentoo. (Actually, install Arch Linux))
 
(In the future, there will be a build server and a Spigot Resource)

(Alternatively, try the [development builds](https://repo.nowak-at.net/xxyy-public/io/github/xxyy/quietcord/))
 
# Configuration

Look at `plugins/QuietCord/config.yml`. If you don't understand that, it has information
at the top on how to get help.

# License

This project is licensed under the MIT license. 
[Please refer to the LICENSE file for details.](https://github.com/xxyy/quietcord/blob/master/LICENSE)
