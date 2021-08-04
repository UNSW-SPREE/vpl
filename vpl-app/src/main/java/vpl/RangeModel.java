package vpl;
/**
 * Title:        Virtual Production Line<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Anna Bruce<p>
 * Company:      UNSW PV Engineering<p>
 * @author Anna Bruce
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.event.*;

/**
 * Based on the source code for DefaultBoundedRangeModel,
 * this class stores its value as a double, rather than
 * an int.  The minimum value and extent are always 0.
 **/
public class RangeModel implements BoundedRangeModel {
    protected ChangeEvent changeEvent = null;
    protected EventListenerList listenerList = new EventListenerList();

    protected int maximum = 10000;
    protected int minimum = 0;
    protected int extent = 0;
    protected double value = 0.0;
    protected boolean isAdjusting = false;
    final static boolean DEBUG = false;

    public RangeModel() {
    }

    public int getMaximum() {
	return maximum;
    }

    public void setMaximum(int newMaximum) {
	setRangeProperties(value, extent, minimum, newMaximum, isAdjusting);
    }

    public int getMinimum() {
	return (int)minimum;
    }

    public void setMinimum(int newMinimum) {
	setRangeProperties(value, extent, newMinimum, maximum, isAdjusting);
    }

    public int getValue() {
	return (int)getDoubleValue();
    }

    public void setValue(int newValue) {
	setDoubleValue((double)newValue);
    }

    public double getDoubleValue() {
	return value;
    }

    public void setDoubleValue(double newValue) {
	setRangeProperties(newValue, extent, minimum, maximum, isAdjusting);
    }

    public int getExtent() {
	return (int)extent;
    }

    public void setExtent(int newExtent) {
	//Do nothing.
    }

    public boolean getValueIsAdjusting() {
	return isAdjusting;
    }

    public void setValueIsAdjusting(boolean b) {
	setRangeProperties(value, extent, minimum, maximum, b);
    }

    public void setRangeProperties(int newValue,
				   int newExtent,
				   int newMin,
				   int newMax,
				   boolean newAdjusting) {
	setRangeProperties((double)newValue,
			   newExtent,
			   newMin,
			   newMax,
			   newAdjusting);
    }


    public void setRangeProperties(double newValue,
				   int unusedExtent,
				   int newMin,
				   int newMax,
				   boolean newAdjusting) {
    minimum = newMin;
	if (newMax <= minimum) {
	    newMax = minimum + 1;
	}
	if (Math.round(newValue) > newMax) { //allow some rounding error
	    newValue = newMax;
	}
	if (Math.round(newValue) < newMin) { //allow some rounding error
	    newValue = newMin;
	}

	boolean changeOccurred = false;
	if (newValue != value) {
	    value = newValue;
	    changeOccurred = true;
	}
	if (newMax != maximum) {
	    maximum = newMax;
	    changeOccurred = true;
	}
	if (newAdjusting != isAdjusting) {
	    maximum = newMax;
            isAdjusting = newAdjusting;
	    changeOccurred = true;
        }

	if (changeOccurred) {
            fireStateChanged();
	}
    }

    /*
     * The rest of this is event handling code copied from
     * DefaultBoundedRangeModel.
     */
    public void addChangeListener(ChangeListener l) {
	listenerList.add(ChangeListener.class, l);
    }

    public void removeChangeListener(ChangeListener l) {
	listenerList.remove(ChangeListener.class, l);
    }

    protected void fireStateChanged() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }
}