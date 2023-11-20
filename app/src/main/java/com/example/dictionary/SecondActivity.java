//Created by Ayman Taleb 11/19/2021

package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity implements Serializable {

    //element declaration
    ImageButton buttonReturn;
    Button buttonClear;
    ImageButton buttonAddWord;
    EditText wordBox;
    EditText wordClassBox;
    EditText wordDefinitionBox;
    EditText wordFreqBox;
    Word newWord = null;
    ArrayList<Word> newWords = new ArrayList<Word>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //finding the elements
        buttonReturn = (ImageButton) findViewById(R.id.homePageButton);
        buttonAddWord = (ImageButton) findViewById(R.id.addWordButton2);
        buttonClear = (Button) findViewById(R.id.clearButton);

        wordBox = (EditText) findViewById(R.id.editTextEnteredWord);
        wordClassBox = (EditText) findViewById(R.id.editTextWordClass);
        wordFreqBox = (EditText) findViewById(R.id.editTextWordFreq);
        wordDefinitionBox = (EditText) findViewById(R.id.editTextWordDef);


        //return button sends user back to MainActivity, if there are new words it will bundle them and send them
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newWords.size() > 0) {
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("WORD_INFO",(Serializable) newWords);
                    intent.putExtra("BUNDLE",bundle);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //clears the EditTexts
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordBox.setText("");
                wordClassBox.setText("");
                wordDefinitionBox.setText("");
                wordFreqBox.setText("");
            }
        });

        //parses the input from the EditTexts, creates a new word object, and adds it to the new words list
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wordFreq = 1;
                String word = wordBox.getText() + "";
                String wordClass = wordClassBox.getText() + "";
                String wordDef = wordDefinitionBox.getText() + "";
                String wordFreqString = wordFreqBox.getText() + "";
                if(!wordFreqString.equals("")) {
                     wordFreq = Integer.parseInt(wordFreqString);
                }
                if(!word.equals("")) {
                    newWord = new Word(word, wordClass, wordDef, wordFreq);
                    newWord.setWordFreq(wordFreq);
                    newWords.add(newWord);
                    wordBox.setText("");
                    wordClassBox.setText("");
                    wordDefinitionBox.setText("");
                    wordFreqBox.setText("");
                }


            }
        });
    }

    public Word getNewWord() {
        return newWord;
    }
}