# My Personal Project

# *Music Organizer*

### What will the application do?
This application is my venture to create a simple organizational tool that allows users to store and retrieve 
information about their favourite songs. See below for specific features I've implemented!

### Who will use it?
Users who want a place to keep track of their music may enjoy this application!

### Why is this project of interest to me?
I'm a *big fan* of music -- I love discovering new songs, creating playlists, and looking at the patterns in my music 
taste. I wanted to create an application for people with a similar passion who would enjoy the features of my app.

## User Stories
As a user, I will be able to...

**In the console version of this app:**
* add songs to my playlist and store details about its artist, 
album, and genre
* remove songs from my playlist
* see a high-level overview of my playlist, particularly its name, 
description, and the titles/artists of all the songs I've added to it
* retreive the full details of a song when viewing my playlists
* save my playlist to file
* reload saved playlist from file

**In the GUI version of this app:**
* add/remove songs to my playlist and store details about its artist,
  album, and genre
* see a high-level overview of my playlist, particularly
  the titles/artists of all the songs I've added to it
* save my playlist to file
* reload saved playlist from file

##Phase 4: Task 2
Event Sample:

"Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: Eventually, Artist: Tame Impala, Album: Currents, Genre: Indie

Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: Cleopatra, Artist: The Lumineers, Album: Cleopatra, Genre: Indie

Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: Ivy, Artist: Frank Ocean, Album: Blonde, Genre: R&B

Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: Space Song, Artist: Beach House, Album: Depression Cherry, Genre: Indie

Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: Bloom, Artist: The Paper Kites, Album: Woodland, Genre: Indie

Fri Apr 01 03:29:58 PDT 2022
Song was added to playlist with Name: What Once Was, Artist: Her's, Album: Songs of Her's, Genre: Indie

Fri Apr 01 03:30:41 PDT 2022
Song was added to playlist with Name: True Colours, Artist: The Weeknd, Album: Starboy, Genre: R&B

Fri Apr 01 03:30:55 PDT 2022
Song was added to playlist with Name: test, Artist: song, Album: to, Genre: remove

Fri Apr 01 03:31:13 PDT 2022
Song was removed from playlist."

##Phase 4: Task 3
The code that I wrote for this project ended up being pretty simple and (in my opinion) organized
well, at least in the model and persistence packages. Because I didn't have
many classes in the model package, there is not much the diagram indicates about clear changes
that would improve the design of my project. But, ideas to make it better are:
* creating an interface or abstract class for my Main methods
* maybe creating an abstract class out of my original MusicApp since the GUI
version used the same classes

  

If I had more time, I would definitely go back and refactor my ui package & console/GUI MusicApp 
classes too. The non-robustness of my implementation is not as clear from the UML diagram itself,
but looking at class relationships and my code revealed weaknesses in my code
I could improve on with more time:
* changing up the implementation of the method I wrote in Playlist to display snippets
of each song, so I wouldn't have to create a brand new method in my UI to have this functionality 
in my console 
* utilize the getter/setter methods in my Song class instead of creating class fields in my GUI
to collect user text inputs
* (not sure if this is possible, so this would just be looking into) creating one method abstraction
that I could've called as a template for creating rows to put in my GUI container, since I ended up 
having a lot of repetitive code with creating buttons/labels that made my
code really long

###Photo Credits:
Happy Cat: https://newtownsquarevet.com/happy-cat-month/

Music Cat: https://www.npr.org/2015/03/07/391377916/these-tunes-are-music-to-your-cats-furry-ears
