How the dump works:

1. Copy all files from the "scripts" folder into the scripts folder of the
   game.
2. Start the english version of X3TC.
3. Start a new game as Terran Defender.
4. Activate the script editor by renaming the player to "Thereshallbewings"
5. Start the script editor and run the script "ailis.dumpAll". This may take
   a minute and feel like the game is frozen. Don't worry, that's normal.
6. You'll now find six log files in the folder "My Documents\Egosoft\X3TC"
   (log00000.txt to log00005.txt). Copy these files into the "en" folder.
   
7. Now switch the game to the foreign language you want to dump and repeat the
   steps 3 to 6 and copy the logfiles into the corresponding folder ("de" for
   German).
   
8. Run the build.xml (Simply "ant" in this directory).

That's it. Now you have the XML files and message property files in the "out"
folder. Move these files to whereever you need them and be happy.