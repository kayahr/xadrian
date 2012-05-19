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

The Windows Setup is only built automatically on Linux when the file
`/usr/share/nsis/Plugins/WinShell.dll` was detected. If your NSIS installation
is not in this standard directory or you are building on Windows and you
are sure that you have configured NSIS correctly then you can force the
NSIS build with `mvn package -P nsis`.

 
Factory Complex Template Codes 
------------------------------

Since version 1.4.3 Xadrian supports exporting and importing factory complexes
to and from template codes. These template codes are very small strings which
can easily be shared via Instant Messaging or Websites or whatever. 

Template codes are base64 encoded byte streams. The stream consists of
dynamically sized unsigned numerical values. The highest bit of each byte 
defines if there are more bytes belonging to the current value. So when bit 7 
is 0 then this is a one-byte value in the range of 0 to 127. When the bit is 1 
then this first byte contains the lower 7 bits of the value and the next byte 
contains additional 7 bits of the value and the highest bit again defines if 
there a more bits in the next byte. 

The stream contains the following values (in this order):

* Template settings bit mask. Currently the following bits are defined: 
    
        Bit   0: 0=No sector selected, 1=Sector selected
        Bit 1-3: 0=X3:TC complex, 1=X3:AP complex
        Bit 4-6: The template version. Currently always 0.
        
* When sector is set then the next two values are the X and Y position of the
  sector. When no sector is set then the next value is the sun power in
  percent.
  
* Next value is the numeric id of the first factory of the complex. This 
  numeric id (nid) can be looked up in the 
  [X3:TC factories.xml file](https://raw.github.com/kayahr/xadrian/master/src/main/resources/de/ailis/xadrian/data/x3tc/factories.xml)
  or in the 
  [X3:AP factories.xml file](https://raw.github.com/kayahr/xadrian/master/src/main/resources/de/ailis/xadrian/data/x3ap/factories.xml).
  
* If the factory is a mine then the next values are the asteroid yields plus
  one. A value of 0 marks the end of the list. If the factory is not
  a mine then the next value defines the quantity of this factory.

* The last step is repeated until a factory numeric id of 0 is read which
  marks the end of the factory list.

### Example ###

Let's say we have a X3:AP complex in the sector Harmony of Perpetuity with
three Terran Silicon Mines L, two Teladi Ore Mines M, two
Teladi Flower Farm M and one Paranid Solar Power Plant Size XL. These are the
unencoded values (with description):

      3   Bit 0 is set because a sector is selected, Bit 1 is set because this 
          is a X3:AP complex.
     14   X coordinate of sector Harmony of Perpetuity
      4   Y coordinate of sector Harmony of Perpetuity
     73   Numeric id of Terran Silicon Mine Size L
     21   Asteroid yield 20 + 1
     20   Asteroid yield 19 + 1
     19   Asteroid yield 18 + 1
      0   End of asteroid yield list
    223   First 7 bits of numeric id 223 of the Paranid Solar Power Plant XL 
          plus bit 7 set to indicate more bits in the next value
      1   The rest of the bits of numeric factory id 223. Bit 7 is now 0 so no 
          more bytes are needed for this value
      1   Quanity of Paranid Solar Power Plant XL in the complex
     17   Numeric ID of Teladi Ore Mine Size M
     19   Asteroid yield 18 + 1 
     17   Asteroid yield 16 + 1
      0   End of asteroid yield list
     22   Numeric ID of Teladi Flower Farm M
      2   Quantity of Teladi Flower Farm M factories in the complex
      0   End of factory list

So in hexadecimal notation the template code is this:

    03 0e 04 49 15 14 13 00  df 01 11 13 11 00 16 02 00
    
Simply base64-encode these bytes and you'll get the template code string:

    dGVzdC50eHQK
    
That's it.