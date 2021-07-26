package vplTest;
import vpl.Cost;
/**
 *
 * @author Quanzhou Yu
 */
public class EquipmentCostTest extends UnitTest{
    public EquipmentCostTest(){
        super("Equipment Cost Test");
    }

    @Override
    public boolean basicTest() {
        boolean result;
        double equipmentCost = 0;
        Cost waferCost = new Cost();
        waferCost.setOverallThroughput(400);
        equipmentCost = waferCost.equipmentCost(2400000, 0.9552, 2880, 0.9965, 0.002);
        result = Math.abs(equipmentCost - 0.0217) <= 0.0001;
        assertion(result, "Basic Test");
        return result;
    }

    @Override
    public void postInitialize() {
    
    }
}
