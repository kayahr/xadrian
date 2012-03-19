Xadrian
=======

Xadrian is a factory complex calculator for the games 
[X3: Terran Conflict](http://www.egosoft.com/games/x3tc/info_en.php) and 
[X3: Albion Prelude](http://www.egosoft.com/games/x3ap/info_en.php). 
With this tool you can plan your factory complexes before you build them in-game.


Build instructions
------------------

### Requirements ###

1. Java JDK 6 or higher. Tested with Oracle Java and OpenJDK. On a Debian
   based Linux system you can install it with the command
   `sudo apt-get install openjdk-6-jdk`.

2. [Apache Maven](http://maven.apache.org/). When installed correctly then you 
   should be able to run `mvn --version` on the command-line. On a Debian 
   based Linux system you can install it with the command 
   `sudo apt-get install maven`
   
3. [Nullsoft Scriptable Install System](http://nsis.sourceforge.net/).
   When installed correctly then you should be able to run `makensis` on the
   command-line. On a Debian based Linux system you can install it with the 
   command `sudo apt-get install nsis`
   
4. Download the [WinShell plugin](http://nsis.sourceforge.net/WinShell_plug-in) 
   and unpack the DLL file from the downloaded ZIP into the `Plugins` folder
   of NSIS. On Linux this is probably `/usr/share/nsis/Plugins`.
   
5. On a 64 bit Linux machine you also need the 32 bit C library to be able to
   run Launch4J. On a Debian-based system you can install it with the command
   `sudo apt-get install libc6-i386`.
   
### Building ###

Just run `mvn package` to compile Xadrian. If everything works well then 
you'll find the Xadrian packages for all platforms in the `target` directory.


