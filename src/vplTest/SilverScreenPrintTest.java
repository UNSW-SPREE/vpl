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
public class SilverScreenPrintTest extends UnitTest{
    public SilverScreenPrintTest(){
        super("Silver Screen Print Test");
    }
    
    @Override
    public boolean basicTest() {
        double silverScreenCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        silverScreenCost = waferCost.silverScreenPrint(110, 20, 22, 2, 20, 150);
        boolean result = Math.abs(silverScreenCost - 0.1487) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    
    }
    
}
