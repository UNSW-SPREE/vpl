package vplTest;
import java.util.ArrayList;
/**
 *
 * @author Quanzhou Yu
 */
public class SelfTest {
    private ArrayList<UnitTest> tests; 
    //add all tests here
    public SelfTest(){
        tests = new ArrayList<UnitTest>();
        tests.add(new EquipmentCostTest());
        tests.add(new InspectionTest());
        tests.add(new EtchTest());
        tests.add(new TextureTest());
        tests.add(new DiffusionTest());
        tests.add(new PlasmaEtchTest());
        tests.add(new ARCoatingTest());
        tests.add(new ElectricityCostTest());
        tests.add(new SilverScreenPrintTest());
        tests.add(new AlScreenSetupTest());
        tests.add(new CofiringTest());
    }
    public void excute(){
        System.out.println("*************************************** ");
        System.out.println("*           Start testing             * ");
        System.out.println("*************************************** ");
        System.out.println(" ");
        ArrayList<Boolean> passes = new ArrayList<Boolean>();
        ArrayList<Boolean> fails = new ArrayList<Boolean>();
        int passNum = 0;
        int failNum = 0;
        for(UnitTest test: tests){
            System.out.println("_______________________________________");
            System.out.println(test.testName);
            test.initialize();
            ArrayList<Boolean> isPass = test.executeTest();
                for(boolean b: isPass){
                    if(b){
                    passes.add(b);
                }else{
                    fails.add(b);
                }
            }
            
        }
        passNum = passes.size();
        failNum = fails.size();
        announceTestResult(passNum, failNum);
    }
    public void announceTestResult(int passNum, int failNum){
        System.out.println("+-------------------------------------+ ");
        System.out.println("|           Test Result               | ");
        System.out.println("+-------------------------------------+ ");
        System.out.println(passNum + " tests passed, " + failNum + " tests failed.");
        if(failNum == 0){
            System.out.println("Congratulations!You passed all the tests!!!");
        }
    }
}
