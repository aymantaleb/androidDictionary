//Created by Ayman Taleb 11/19/2021
//Word object to manage and store the data for each word
package com.example.dictionary;

import java.io.Serializable;

public class Word implements Serializable {

    private String word;
    private String wordClass;
    private String wordDef;
    private int wordFreq = 1;

    //constructor
    public Word(String word, String wordClass, String wordDef, int wordFreq){
        this.word = word;
        this.wordClass = wordClass;
        this.wordDef = wordDef;
        this.wordFreq = wordFreq;
    }


    public int getWordFreq() {
        return wordFreq;
    }

    public String getWord() {
        return word;
    }

    public String getWordClass() {
        return wordClass;
    }

    public String getWordDef() {
        return wordDef;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWordClass(String wordClass) {
        this.wordClass = wordClass;
    }

    public void setWordDef(String wordDef) {
        this.wordDef = wordDef;
    }

    public void setWordFreq(int wordFreq) {
        this.wordFreq = wordFreq;
    }


}
