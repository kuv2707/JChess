------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:CHESSAYUSH
PURPOSE OF PROJECT:implementation of chess game through OOP concepts
VERSION or DATE:fdsfdsafsl;fjsfs
HOW TO START THIS PROJECT:entryPoint.main();
AUTHORS:Kislay Udbhav Verma 
USER INSTRUCTIONS:
none

absolutely necessary:
Open a service socket to sync game settings with the online player
font files as pictures of goti(printing their tipo which is font representation of their appearance)  , as a fallback option if pictures could not be loaded
  or even in general
    and theme change changes the font accordingly  (leipfont is the only one installed)
AN ORGANIZED WAY OF ENDING GAMES, WITH COMMUNICATION WITH OPPOSITE PARTY IN ONLINE MODE
SYNCHRO. OF TIME WITH OPPOSITE PARTY
GAME ENGINE COULD PLAY 2 MOVES AT ONCE, CHECK IF RESOLVED
TIME SYNCHRONIZATION IN LAN GAME only happens if host presses,drags or released a goti on board
SEND THE OPPOSITE PARTY REASON OF ENDING GAME (LIKE TIMEOUT,MANUALLY QUIT ETC) WHEN THAT HAPPENS, IN LAN MODE
message system in lan doesnt work
ANIMRESULT MESSAGE SIZE ADJUST WHEN PLAYER NAME ETC ARE DISPLAYED
GAME.ENDGAME DOES NOT SHOW MESSAGE THROUGH ANIMRESULT
JTABLE OF MOVES
LOW RESOURCES MODE/BASIC MODE - without so much animation

the mobile highlighter has defects

setting panel for turning on off rgb,sounds  
move history table
change theme combobox revamp show pictures of various themes
show message when player loses due to lack of pieces to force checkmate
declutter gui class drawing graphics section

help button for telling how to castle etc
REPLAY MODE ME CHAT BHI SHOW KARO

in online mode, if one player quits manually, inform the other, and perform game closing routime(showing anim of disconnected etc);

change board square colors when changing theme of gotis

rehighlight on mouse release  (the squares of gotis)

give option to save a game on clicking on close button, preferrably in PGN format
send string instead of object in network mode, just like how files are saved
dont hardcode location of stockfish engine , image save directory etc
rather put them relative to the jar file
test game recording behaviour when game is ended successfully and not in incomplete state 
why did that problem occur? hanging of gui when human released goti and ai moved, but not even could move, and transition thread goti became equal to that picked by ai etc
button to turn on off rgb
get hd images of gotis, not 80*80 resized ones.
scripted chat with AI   DO IT
entrypoint frame taskbar icon
change appearance of gotis menu redesign visual show images
maybe replace whole board image with small images of white and black spots,and custom drawing the algebraic coordinates(a1,b3 etc) in a strip


draw GIFs

selcol should be drawn for each location on board, so that after turn completion, the next player gets a blue highlight (selcol ) on goti under his mouse
(minor thing though)
like   mousemoved me wo location ke saath color associate ho jayega, and wo color us square of board pe tab hi paint hoga jab us jagah ka goti is turn wale team 
ka hoga  . this ensures independence from other players actions. Such an independence was also achieved on decentralizing chaal's drawing into each goti same do
here but with locations on board: decentralize them from selcol to each having their own characteristic, visible under valid conditions
please dont forget this context o te moriras de desesperanza


 Point p = MouseInfo.getPointerInfo().getLocation();//study it

Problems:


font metrics is useful



put gifs in jpanel


game state parity check from time to time do
