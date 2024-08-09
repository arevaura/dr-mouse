# Dr. Mouse 🪤

## A Simple Journalling Desktop Application

> Do you ever need a space to rant to someone without judgement? Do you like cute animals?  
> 
> Then Dr. Mouse may be just the right therapist for *you*!  

Dr. Mouse is welcoming, understanding, and a great listener overall. With Dr. Mouse, you can log journal entries limitlessly and note how your mood changes with each day.

*Main Features:*
- tracks total number of logged entries
- can store and retrieve all logged entries [that have not been removed]
- lets users create, edit, and delete entries
- stores metadata for each entry:
   - date logged (*required*; automatic)
   - entry title (*optional*; user-generated)
   - content of entry (*required*; user-generated)
   - mood at time of entry creation (*optional*; user-generated)

**What does the application do?**  
This application is a platform that will allow users to express their daily thoughts and feelings. The 'Dr. Mouse' desktop app can take in and retrieve written entries. Users can log as many entries as they would like. Additionally, they can go back and modify or delete entry contents afterwards.  
`Classes: Journal, Entry`

**Who can use this?**  
This free application is intended for the general public. Netizens can explore the app features freely and at their own leisure.

**Why is this project of interest to me?**  
Since the global pandemic hit a few years ago, people have started to pay greater attention to their emotional well-being. Many have found that they especially lacked social comfort during the lockdown. As a way to remedy some feelings of isolation, this app idea was born to help everyone express themselves without any restrictions or limits. Users can release their pent-up thoughts and feelings with the internet right at your fingertips, and that is why this project is important to me.  
##### Remember, Dr. Mouse is always only a few mouse-clicks away! 🖱️

***

###### *Minimum Viable Project:* A simple journalling desktop application that allows users to input and delete written log entries. Metadata will be noted and modifiable for each saved entry.

***

<img width="200" alt="" src="https://media.github.students.cs.ubc.ca/user/25154/files/b0230aeb-abe4-416b-a635-8c03b79f7bb6">


#### Why choose Dr. Mouse?  
Mice are intellectual creatures. Much like us, some prefer to stay close to peers and others prefer solitude. Mice have lots of personality, and are thought to exhibit emotional intelligence and empathy as well. Rest assured, Dr. Mouse provides an excellent safe space for anyone and everyone!  
[=🐭=](https://phys.org/news/2019-11-scientists-mouse-personality.html#:~:text=Some%20are%20quick%20to%20explore,life%20and%20define%20their%20personality)  
[🐁.....](https://www.earth.com/news/mice-pass-the-mirror-test-does-that-mean-they-are-self-aware/)   

***

*User Stories*
- As a user, I want to be able to track the total number of entries logged.
- As a user, I want to be able to store and retrieve all logged entries [that have not been removed].
- As a user, I want to be able to create log entries.
- As a user, I want to be able to delete log entries.
- As a user, I want to be able to modify log entries and their metadata properties.
   - stored metadata for each entry:
      - date logged (*required*; automatic)
      - entry title (*optional*; user-generated)
      - content of entry (*required*; user-generated)
      - mood at time of entry creation (*optional*; user-generated)
- As a user, I want to be able to save my log entries to file and have the option to do so or not.
- As a user, I want to be given the option to load my log entries from file and continue editing them.  

# Instructions for Grader

- You can generate the first required action related to the user story "adding an entry to the journal" by clicking the `Write Entry` button at the start of the application.
- You can generate the second required action related to the user story "deleting an entry from the journal" by clicking the `Delete Entry` button on the entry log page (click any `View Entries` button to get to this page). Then, click from the list at the side the entry you would like to delete.
- You can locate my visual component at the start of the application (*check out Dr. Mouse's wonderful headshot*). 
- You can save the state of my application by clicking the `View Entries` button to reach the entry log page and then clicking the `Save Entries` button.
- You can reload the state of my application by clicking the `Load Saved Entries` button at the start.  

# Phase 4: Task 2

Representative sample of the events that occur when this program runs:  

<img width="460" alt="EventLogSampleScreenshot 2024-08-08 165258" src="https://media.github.students.cs.ubc.ca/user/25154/files/4cb8b835-04fd-43db-85d2-5941a3a3ce21">  
*Note that despite the event descriptions the event log only prints to the terminal after the session is terminated.*  

# Phase 4: Task 3

*Please reference the UML_Design_Diagram file in the root folder for the following reflection below.*  
*Note that grey boxes indicate static classes and that JournalUI handles the GUI while UserHandler manages the console program.*  

If I had more time to work on this project, one thing I would do is remove any associations from the UI and GUI classes that point to the `Entry` class. Since `Journal` is already associated with `Entry`, and `JournalUI` as well as `UserHandler` are associated with `Journal`, the extra associations to `Entry` are redundant and introduce unnecessary coupling in the code. Removing these associations would make the design cleaner.  

Additionally, I would create a new class to handle saving and loading functions involving the `JsonWriter` and `JsonReader` classes. Again, both the GUI and primary console class contain duplicate methods that deal with this. Allocating the saving and loading functionality to be extra responsibilities of both these classes makes the purpose of them less clear and focused. Although it may initially seem unintuitive to introduce a new class, it would ultimately improve the cohesion of the design.  

<img width="600" alt="UML_Design_Diagram" src="https://media.github.students.cs.ubc.ca/user/25154/files/1613e915-1578-47ae-b5e7-f2cd02097930">  
<img width="600" alt="UML_Design_Diagram_Improved" src="https://media.github.students.cs.ubc.ca/user/25154/files/2d192055-8f5a-4ab2-b7d2-c0866aed1e25">  


###### `©jieva 2024`
