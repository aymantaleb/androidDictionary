Dictionary by Ayman Taleb 11/19/2021

All files needed are in the zip provided. 
All the online resources I used are listed in the statement.txt file



Dictionary:
The dictionary app features a main activity layout which includes a search bar, boxes for frequently used words, and a RecyclerView list that displays words from an "words" ArrayList. 
When a user selects a word from the RecyclerView, a popup window appears, showing details like the word's class, frequency, and definition. 
Users can remove a word from the list using a minus sign button in the popup, which also removes the word from both the ArrayList and RecyclerView.
The popup can be closed without removing the word by clicking anywhere outside it. The app's search functionality works by parsing the text input in the search bar. 
If the word list contains entries and there's text in the search box, the app copies the main list, sorts it by frequency, and then filters it to find and display elements matching the entered text in the frequent words boxes. 
These words in the frequent words boxes are clickable and open a popup similar to the RecyclerView, but without a remove option. Additionally, there is a plus button at the bottom of the layout for adding new words, which opens a new activity. In this 'add word' activity, users can enter multiple new words, which are stored in an ArrayList and added to the original words list upon returning to the main activity.