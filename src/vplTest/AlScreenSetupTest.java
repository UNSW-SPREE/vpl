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
public class AlScreenSetupTest extends UnitTest{
    public AlScreenSetupTest(){
        super("Al Screen Setup Test");
    }

    @Override
    public boolean basicTest() {
        double alScreenSetupCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        alScreenSetupCost = waferCost.alScreenSetup(110,20,23,20,"");
        boolean result = Math.abs(alScreenSetupCost - 0.14187) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize(){
    
    }
    
}
