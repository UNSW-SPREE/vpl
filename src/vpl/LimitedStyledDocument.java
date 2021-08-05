package vpl;
//for the notebook
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;


public class LimitedStyledDocument extends DefaultStyledDocument {

    int maxCharacters;

    public LimitedStyledDocument(int maxChars) {
        maxCharacters = maxChars;
    }

    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException {

        //This rejects the entire insertion if it would make
        //the contents too long. Another option would be
        //to truncate the inserted string so the contents
        //would be exactly maxCharacters in length.
        if ((getLength() + str.length()) <= maxCharacters)
            super.insertString(offs, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }
}