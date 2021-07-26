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
public class CofiringTest extends UnitTest{
    public CofiringTest(){
        super("Cofiring Test");
    }
    @Override
    public boolean basicTest() {
        double cofiringCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        cofiringCost = waferCost.cofiring(8, 250, 600, 800, 600, 10, 30);
        boolean result = Math.abs(cofiringCost - 0.005681) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    
    }
    
    
}
