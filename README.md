# VGU-Local-Power-Simulation
# Setup git for easier version control. Because facebook upload get messy quick
1. follow this link to setup git and config your account 
https://help.github.com/articles/set-up-git/#setting-up-git
2. go to your desire work folder
3. type the following.
```
  git remote add origin https://github.com/deadmanproqn/VGU-Local-Power-Simulation.git
  git remote -v
 ```
 now you have connected that folder to this project
 4. in git bash or terminal(if you add git to PATH). type
 ```
  git pull origin
 ```
 5. alway keep master branch clear
 6. to create a branch. type
 ```
  git branch <branch name> // this create a branch
  git checkout <branch name> //move your workspace to that new branch
 ```
 7. to add a file/folder to your "i wish to upload these files/folders". type
 ```
  git add <file/folder path>
 ```
 8. to finalize your work. type 
 ```
  git commit
 ```
 this will open vim in your terninal to give a message(this is required to push to the net). google "how to use vim" if you have no idea what this is
 9. now you push it for review. just type
 ```
  git push origin <branch name>
 ```
### Report Structure
 
 - [x] Abstract
 - [x] Chapter 1: intro
 - [x] |-Motivation
 - [x] |-Objective
 - [x] |-Outline
 - [ ] Chapter 2: Software analysis
 - [ ] |- Architecture
 - [ ] |- Use-Case analysis
 - [ ] |- Class Diagram
 - [ ] Chapter 3: Code explanation
 - [ ] |- Consumer
 - [ ] |- Generator
 - [ ] |- Control
 - [ ] |- Graphical User Interface
 - [ ] Chapter 4: Conclusion
