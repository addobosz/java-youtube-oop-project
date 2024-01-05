# Good Tube App

### Short description:

The following project was assigned due to Object Programming university course. 
It is coded entirely in java. Some part of GUI was prepared using .form file. 

### Instruction:

-  **How to run the project?** Run the jar file using the command 
> java -jar .\out\artifacts\java_youtube_oop_project_jar\java-youtube-oop-project.jar

or use dedicated java IDE (remember to set src folder as the source folder).

- **How to start the simulation?** Select the number of users and channels in the setup dialog. I recommend choosing around 10 of each, for the sake of convienience (graphical interface is not meant to handle enormous amounts of users, although the program could do that). 

- **What is going on?** Important events like start of a stream or a new video being added are mentioned in the user's terminal. These actions may be spectated using the GUI - including teh details of all major entities (UserAccounts, Channels, Videos, Streams), by choosing a particular user and pressing a desired button. 

- **How does the serialization work in this project?** Saving and loading a state are realized, respectively, by the serialization and deserialization techniques. Saved states will immidiately appear in the outer-most directory of the project. In order to use "Load" function, one has to put "simulationManager.ser" (name matters in this case) file in the java-youtube-oop-project directory and press the "Load" button while in the simulation.

- **Documentation?** For documentation, please go to javadoc folder and choose package-summary.html file. 


### Features
- each channel and user is a separate thread,
- videos and streams are played in real-time,
- progress of the simulation may be watched from the GUI,
- entities might be searched using the search panel available in the user interface,
- saving and loading state,
- users may like videos and subscribe channels randomly. Moreover, users have queue in which the next videos to be played are stored,
- channels start streams at random moments and add new videos. 

### Sources
User pictures were acquired from https://www.iconarchive.com/, and are free for non-comercial usage.

Video thumbnails were acquired from https://www.freepik.com/, with free license.
