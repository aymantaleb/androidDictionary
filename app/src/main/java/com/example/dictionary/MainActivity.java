//Created by Ayman Taleb 11/19/2021


package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.*;
import java.io.*;


public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

    //element declaration
    ImageButton buttonSearch;
    ImageButton buttonAddWord;
    TextView freqWord1;
    TextView freqWord2;
    TextView freqWord3;
    EditText enteredWord;
    RecyclerAdapter recyclerAdapter;
    ArrayList<String> fileLines = new ArrayList<>();
    public  ArrayList<Word> words = new ArrayList<Word>();
    InputStream inputStream;
    Word topWord1, topWord2, topWord3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //recieving info from second activity, arraylist of new words added
        Bundle extras = getIntent().getBundleExtra("BUNDLE");

        //checks to see if the bundle is empty, if not it will parse the list of new words and add them to the main word list
        if(extras != null) {
            ArrayList<Word>  tempWords = (ArrayList<Word>) extras.getSerializable("WORD_INFO");
            for(int i = 0; i < tempWords.size(); i++){
                words.add(tempWords.get(i));
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test words
        Word w1 = new Word("programming","verb","test",100);
        Word w2 = new Word("process","verb","test",350);
        Word w3 = new Word("progressive","adjective","test",500);
        Word w4 = new Word("productve","adjective","test",200);
        Word w5 = new Word("care","verb","test",100);
        Word w6 = new Word("car","noun","test",50);
        Word w7 = new Word("cat","noun","test",600);
        Word w8 = new Word("universe","noun","test",69);
        Word w9 = new Word("university","noun","test",420);
        Word w10 = new Word("universal","adjective","test",55);
        words.add(w1);
        words.add(w2);
        words.add(w3);
        words.add(w4);
        words.add(w5);
        words.add(w6);
        words.add(w7);
        words.add(w8);
        words.add(w9);
        words.add(w10);
        topWord1 = null;
        topWord2 = null;
        topWord3 = null;




        //finding the elements
        buttonSearch = (ImageButton) findViewById(R.id.searchButton);
        buttonAddWord = (ImageButton) findViewById(R.id.addWordButton);
        freqWord1 = (TextView) findViewById(R.id.editTextFrequentWord1);
        freqWord2 = (TextView) findViewById(R.id.editTextFrequentWord2);
        freqWord3 = (TextView) findViewById(R.id.editTextFrequentWord3);
        enteredWord = (EditText) findViewById(R.id.editTextEnteredWord);

        //RecyclerView is used for the word list
        RecyclerView recyclerView = findViewById(R.id.wordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(this,words); //RecyclerAdapter for converting arraylist into viewable list in RecyclerView
        recyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);//adds border around RecyclerView elements
        recyclerView.addItemDecoration(dividerItemDecoration);





        //search button
        /* Parses the text input in the search box, if there are any words in the word list and if there is text in the search box
        * it will copy the main list, sort the copy based on frequency, then sort through the sorted list and find all the elements
        * that match the entered text and finally add them to a new list which puts them in the boxes*/
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                freqWord1.setText("");
                freqWord2.setText("");
                freqWord3.setText("");
                topWord1 = null;
                topWord2 = null;
                topWord3 = null;

                String wordSearched = enteredWord.getText() + "";
                int wordSearchedLength = wordSearched.length();
                ArrayList<Word> sortedWords = new ArrayList<>(words);
                ArrayList<Word> topThreeWords = new ArrayList<>();
                if (words.size() >= 1 && !wordSearched.equals("")) {
                    Word temp = null;
                    for (int i = 0; i < sortedWords.size(); i++) {
                        for (int j = 1; j < (sortedWords.size() - i); j++) {
                            if (sortedWords.get(j - 1).getWordFreq() < sortedWords.get(j).getWordFreq()) {
                                temp = sortedWords.get(j - 1);
                                sortedWords.set(j-1,sortedWords.get(j));
                                sortedWords.set(j,temp);
                            }
                        }
                    }
                    if(topThreeWords.size() > 0){

                        topThreeWords.clear();
                    }

                    for (int i = 0; i < sortedWords.size(); i++) {
                        while(wordSearchedLength > sortedWords.get(i).getWord().length()){
                            wordSearchedLength = wordSearchedLength - sortedWords.get(i).getWord().length();
                        }
                        if (sortedWords.get(i).getWord().substring(0,wordSearchedLength).contains(wordSearched)) {
                            topThreeWords.add((sortedWords.get(i)));
                        }
                    }

                    if(topThreeWords.size() >= 3) {
                        freqWord1.setText(topThreeWords.get(0).getWord());
                        freqWord2.setText(topThreeWords.get(1).getWord());
                        freqWord3.setText(topThreeWords.get(2).getWord());
                        topWord1 = topThreeWords.get(0);
                        topWord2 = topThreeWords.get(1);
                        topWord3 = topThreeWords.get(2);
                    }
                    else if(topThreeWords.size() == 2){
                        freqWord1.setText(topThreeWords.get(0).getWord());
                        freqWord2.setText(topThreeWords.get(1).getWord());
                        topWord1 = topThreeWords.get(0);
                        topWord2 = topThreeWords.get(1);
                    }
                    else if(topThreeWords.size() == 1){
                        freqWord1.setText(topThreeWords.get(0).getWord());
                        topWord1 = topThreeWords.get(0);
                    }
                    topThreeWords.clear();
                    sortedWords.clear();

                }
            }
        });

        //add word button opens SecondActivity
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }
        });


        //frequent word boxes are clickable to show the word, word class, frequency, and definition
        freqWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topWord1 != null){
                    String word = topWord1.getWord();
                    String wordClass = topWord1.getWordClass();
                    String wordDef = topWord1.getWordDef();
                    int wordFreq = topWord1.getWordFreq();
                    String wordFreqString = "Frequency: " + String.valueOf(wordFreq);

                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.word_popup_window2, null);
                    TextView wordBox = popupView.findViewById(R.id.editTextWordBox);
                    TextView wordClassBox =  popupView.findViewById(R.id.editTextWordClassBox);
                    TextView wordFreqBox = popupView.findViewById(R.id.editTextWordFreqBox);
                    TextView wordDefBox = popupView.findViewById(R.id.editTextWordDefBox);


                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


                    wordBox.setText(word);
                    wordClassBox.setText(wordClass);
                    wordFreqBox.setText(wordFreqString);
                    wordDefBox.setText(wordDef);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.setElevation(50);
                    }

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            }
        });

        //frequent word boxes are clickable to show the word, word class, frequency, and definition
        freqWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topWord2 != null){
                    String word = topWord2.getWord();
                    String wordClass = topWord2.getWordClass();
                    String wordDef = topWord2.getWordDef();
                    int wordFreq = topWord2.getWordFreq();
                    String wordFreqString = "Frequency: " + String.valueOf(wordFreq);

                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.word_popup_window2, null);
                    TextView wordBox = popupView.findViewById(R.id.editTextWordBox);
                    TextView wordClassBox =  popupView.findViewById(R.id.editTextWordClassBox);
                    TextView wordFreqBox = popupView.findViewById(R.id.editTextWordFreqBox);
                    TextView wordDefBox = popupView.findViewById(R.id.editTextWordDefBox);


                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


                    wordBox.setText(word);
                    wordClassBox.setText(wordClass);
                    wordFreqBox.setText(wordFreqString);
                    wordDefBox.setText(wordDef);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.setElevation(50);
                    }

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            }
        });


        //frequent word boxes are clickable to show the word, word class, frequency, and definition in a pop up window
        freqWord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topWord3 != null){
                    String word = topWord3.getWord();
                    String wordClass = topWord3.getWordClass();
                    String wordDef = topWord3.getWordDef();
                    int wordFreq = topWord3.getWordFreq();
                    String wordFreqString = "Frequency: " + String.valueOf(wordFreq);

                    // inflate the layout of the popup window
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.word_popup_window2, null);
                    TextView wordBox = popupView.findViewById(R.id.editTextWordBox);
                    TextView wordClassBox =  popupView.findViewById(R.id.editTextWordClassBox);
                    TextView wordFreqBox = popupView.findViewById(R.id.editTextWordFreqBox);
                    TextView wordDefBox = popupView.findViewById(R.id.editTextWordDefBox);


                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


                    wordBox.setText(word);
                    wordClassBox.setText(wordClass);
                    wordFreqBox.setText(wordFreqString);
                    wordDefBox.setText(wordDef);



                    //popup window shadow
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.setElevation(50);
                    }

                    // show the popup window
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            }
        });

    }





    //making the elements in the RecyclerView clickable and creates a popup window for the word
    @Override
    public void onItemClick(View view, int position) {

        String word = recyclerAdapter.getItem(position).getWord();
        String wordClass = recyclerAdapter.getItem(position).getWordClass();
        String wordDef = recyclerAdapter.getItem(position).getWordDef();
        int wordFreq = recyclerAdapter.getItem(position).getWordFreq();
        String wordFreqString = "Frequency: " + String.valueOf(wordFreq);



        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.word_popup_window, null);
        TextView wordBox = popupView.findViewById(R.id.editTextWordBox);
        TextView wordClassBox =  popupView.findViewById(R.id.editTextWordClassBox);
        TextView wordFreqBox = popupView.findViewById(R.id.editTextWordFreqBox);
        TextView wordDefBox = popupView.findViewById(R.id.editTextWordDefBox);
        ImageButton buttonRemoveWord = popupView.findViewById(R.id.removeWordButton);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        wordBox.setText(word);
        wordClassBox.setText(wordClass);
        wordFreqBox.setText(wordFreqString);
        wordDefBox.setText(wordDef);






        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        //remove word, removes the word from the arraylist and RecyclerView, and closes the popup window
        buttonRemoveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word removedWord = recyclerAdapter.getItem(position);
                words.remove(removedWord);
                recyclerAdapter.notifyItemRemoved(position);
                popupWindow.dismiss();


            }
        });

        //popup window shadow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(50);
        }
        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}