# SpaceProtector
A Space Invaders like game with steering behaviors implemented.
## Game describe
A Space Invaders like game with steering behaviors implemented. The objective is player control the Protector destroy all invaders. The invaders are separeate into two echelons, each echelon has one leader and two wingmans. Protector should destroy the leader then can eliminate whole invader echelon. <br />
Invaders will fly around and get closer to the Protector, if the Protector does not eliminate both invader echelons before they invade, then the mission will fail, if the Protector eliminates both invader echelons before they invade, then the mission will accomplish.
## Game control
Use key "a" and "d" to move the Protector left and right, and press key "j" to shoot bullet.
## Steering behavior
The Flee Steering Behavior helps invaders flee from the chasing of protector. When the protector move around invaders, they will move in opposite direction, and accelerate for a while.<br />
The Arrive Steering Behavior actions when the protector shoot a bullet. When the protector shoots, invaders will still move in original direction, and slow down for a while.<br />
The Flocking Steering Behavior actions when the protector move close to invaders, and it depends on the distance between the protector and invaders. If the group of invader is far from the protector, two wingmans gather to the lead plane, form a cohesion formation. If the group of invader is close to the protector, two wingmans separated from the lead plane, form a separation formation.
## BUGs report
Sometimes when shooting continuously, the leader cannot be destroyed, even if the leader is accurately hitted.
## Compile and Run
main function in SpaceProtector.java<br />
If you use Eclipse, simply compile and run the SpaceProtector file to start the game.<br />
If you use Linux, you should from Terminal install open jdk<br />
*sudo apt-get install openjdk-7-jdk*<br />
To compile use this command from the terminal<br />
*javac SpaceProtector.java*<br />
If everything works well then a new "SpaceProtector.class" file should be created.<br />
To run the game that you've just compiled type the command below in terminal:<br />
*java SpaceProtector*
