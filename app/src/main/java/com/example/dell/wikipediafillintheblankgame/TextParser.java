/**
 * The purpose of this class is to parse the data and shuffle the words that are going to be filled.
 */

package com.example.dell.wikipediafillintheblankgame;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TextParser {
    static class GameOptions {
        String[] answers;
        String[] options;
        Integer[] starts;
        Integer[] ends;
        String content;
    }

    // Return at least 10 sentences
    public static GameOptions getGameOptions(String content) {
        // TODO Auto-generated method stub
        GameOptions gameOptions = new GameOptions();
        List<String> answers = new ArrayList<String>();
        List<Integer> starts = new ArrayList<Integer>();
        List<Integer> ends = new ArrayList<Integer>();

        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.US);
        BreakIterator wordIterator = BreakIterator.getWordInstance(Locale.US);

        sentenceIterator.setText(content);
        int i = 0;

        int start = sentenceIterator.first();
        int end = sentenceIterator.next();

        outer:
        for (;end != BreakIterator.DONE;
             start = end, end = sentenceIterator.next()) {
            String sentence = content.substring(start, end);
            wordIterator.setText(sentence);
            List<Word> possibleWords = new ArrayList<Word>();
            int wstart = wordIterator.first();
            for (int wend = wordIterator.next(); wend != BreakIterator.DONE; wstart = wend, wend = wordIterator.next()) {
                if (wend - wstart != 7) {
                    continue;
                }
                String word = sentence.substring(wstart, wend);
                possibleWords.add(new Word(word, wstart, wend));
            }
            Collections.shuffle(possibleWords);
            for (Word word : possibleWords) {
                if (answers.contains(word.word)) {
                    continue; // to next word
                }

                answers.add(word.word);
                starts.add(start + word.start);
                ends.add(start + word.end);
                if (++i == 10) {
                    break outer; // stop parsing
                } else {
                    break; // to next sentence
                }
            }

        }
        gameOptions.content = content.substring(0, end);
        gameOptions.answers = answers.toArray(new String[0]);
        Collections.shuffle(answers);
        gameOptions.options = answers.toArray(new String[0]);
        gameOptions.starts = starts.toArray(new Integer[0]);
        gameOptions.ends = ends.toArray(new Integer[0]);

        return gameOptions;
    }

    private static class Word {
        int start;
        int end;
        String word;
        Word(String word, int start, int end) {
            this.word = word;
            this.start = start;
            this.end = end;
        }
    }

}
