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
public class PlasmaEtchTest extends UnitTest{
    public PlasmaEtchTest(){
        super("Plasma Etch Test");
    }

    @Override
    public boolean basicTest() {
        double plasmaEtchCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        plasmaEtchCost = waferCost.plasmaEtch(150, 20);
        boolean result = Math.abs(plasmaEtchCost - 0.00680) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize(){
    
    }
    
}
