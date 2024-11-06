//this readme file is the exact same as README.md in the github repo(which is currently private), made for the convenience of the TAs. 

# Angry Birds Style Game - README
## Project Overview
This project is a simplified version of the popular Angry Birds game, developed using LibGDX, a cross-platform game development framework.
 The game consists of a main launcher screen with settings and play buttons, followed by gameplay in the first level, where players aim to hit targets with birds.

Screens and Transitions

1. Starting Screen (firstScreen.java)

Description The initial screen displayed when the game launches.
Transition Click the Play button to move to the Levels Screen.


2. Levels Screen (levelsScreen.java)

Description Displays options for selecting game levels (currently showing three levels).
Transition Click on Level 1 to proceed to the First Level Screen (Level 1 is the only level with implemented GUI so far).

3. First Level Screen (firstLevel.java)

Description The main game screen for Level 1, featuring gameplay elements and the pause option.
Transitions
Pause Button Click to open the Pause Screen.
VictoryLoss Screen Press the Right Arrow Key on your keyboard to navigate to either the Victory or Loss screen.

4. Pause Screen (pauseScreen.java)

Description Offers options to resume or go back to the main menu.
Transition From the Pause Screen, you can choose options to either resume gameplay or return to the Starting Screen.

5. Victory and Loss Screens

Description These screens display the results of the level (Victory or Loss).
Transition While on the First Level Screen, press the Right Arrow Key to navigate to these screens.


## Assets and Resources
1. Images Used for background, buttons, birds, wood blocks, and other in-game elements.
2. Audio Background music for main menu and gameplay screens.

## Controls
1. Play Button Launches the game level.
2. Settings Button Opens the settings screen.
3. Pause Button (in-game) Pauses the game and opens the pause menu.

## Setup Instructions
### Prerequisites
- Java Development Kit (JDK 8 or higher)
- LibGDX framework
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Steps
1. Clone or download the project files.
2. Import the project into your preferred IDE.
3. Ensure LibGDX dependencies are properly configured.
4. Run the project to start the game.

## Future Improvements
1. Add more levels and diverse obstacles.
2. Implement additional gameplay mechanics (e.g., bird launching physics).
3. Develop a scoring system and winlose conditions.
4. Change of assets to beautify the game.
