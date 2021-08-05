package vpl;
import javax.swing.*; 
import javax.swing.text.*; 

import java.awt.Toolkit;
import java.text.*;
import java.util.Locale;

// You can attach any format to this document.
// Only text that can be parsed by the format is
// allowed in the document.
// Thus this class is general enough to handle dates,
// integers, percents, money, strings, anything for
// which you have a format.
// You could even write your own phone number or
// SSN format classes to handle those types.
// Textfields that use this type of document.
// are locale-sensitive,
// because the formats provided by the JDK
// are locale-sensitive.

public class FormattedDocument extends PlainDocument {

    private Format format;

    public FormattedDocument(Format f) {
	format = f;
    }

    public Format getFormat() {
	return format;
    }

    public void insertString(int offs, String str, AttributeSet a) 
	throws BadLocationException {

	String currentText = getText(0, getLength());
	String beforeOffset = currentText.substring(0, offs);
	String afterOffset = currentText.substring(offs, currentText.length());
	String proposedResult = beforeOffset + str + afterOffset;

	try {
	    format.parseObject(proposedResult);
	    super.insertString(offs, str, a);
	} catch (ParseException e) {
            Toolkit.getDefaultToolkit().beep();
	    System.err.println("insertString: could not parse: " + proposedResult);
	}
    }

    public void remove(int offs, int len) throws BadLocationException {
	String currentText = getText(0, getLength());
	String beforeOffset = currentText.substring(0, offs);
	String afterOffset = currentText.substring(len + offs, currentText.length());
	String proposedResult = beforeOffset + afterOffset;

	try {
	    if (proposedResult.length() != 0)
	        format.parseObject(proposedResult);
	    super.remove(offs, len);
        } catch (ParseException e) {
            Toolkit.getDefaultToolkit().beep();
	    System.err.println("remove: could not parse: " + proposedResult);
        }
    }
}