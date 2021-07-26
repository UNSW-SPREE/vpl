package vplTest;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author Quanzhou Yu
 */
abstract public class UnitTest {
    protected String testName;
    protected ArrayList<Method> subTests;
    public UnitTest(String name){
        testName = name;
        subTests = new ArrayList<Method>();
    }
    public void initialize(){
        addTest("basicTest");
        postInitialize();
    }
    protected abstract void postInitialize();
    public ArrayList<Boolean> executeTest(){
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        for(Method test: subTests){
            try{
                boolean testResult = false;
                testResult = (boolean) test.invoke(this, new Object[]{});
                result.add(testResult);
            }catch(java.lang.IllegalArgumentException e){
                e.printStackTrace();
            }catch(java.lang.IllegalAccessException e){
                e.printStackTrace();
            }catch(java.lang.reflect.InvocationTargetException e){
                e. printStackTrace();
            }
        }
        return result;
    }
    abstract public boolean basicTest();
    public void assertion(Boolean value, String testName){
        try{
            assert value: testName;
            System.out.println("Test Passes: " + testName);
        }catch(java.lang.AssertionError e) {
            System.out.println("Test Fails: " + testName);
        }
    }
    protected void addTest(String testName){
        try{
            Class[] parameters = new Class[0];
            Method basicTest = this.getClass().getDeclaredMethod(testName, parameters);
            subTests.add(basicTest);
        }catch(java.lang.NoSuchMethodException e){
            e.printStackTrace();
        }
    }
}
