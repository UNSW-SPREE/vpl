/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vplTest;

import vpl.Cost;

/**
 *
 * @author Quanzhou Yu
 */
public class EtchTest extends UnitTest{
    public EtchTest(){
        super("Etch Test");
    }
    @Override
    public boolean basicTest() {
        double etchCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        etchCost = waferCost.etch(80, 30, 20);
        boolean result = Math.abs(etchCost - 0.0207) <= 0.0001;
        assertion(result,  "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
        addTest("temperatureChangeTest");
    }
    public boolean temperatureChangeTest(){
        boolean result = false;
        double etchTempChangeCost = 0;
        Cost waferCost = new Cost();
        etchTempChangeCost = waferCost.etch(20, 30, 20);
        result = Math.abs(etchTempChangeCost - 0.0188) <= 0.0001;
        assertion(result,  "Temperature Change Test ");   
        return result;
    }
}
