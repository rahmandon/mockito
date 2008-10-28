package org.mockito.internal.returnvalues;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.configuration.experimental.ConfigurationSupport;
import org.mockitoutil.TestBase;


public class UsingGlobalConfigReturnValuesTest extends TestBase {
    
    public enum Cheese { Edam, Cheddar }
    
    interface CheeseShop {
        Cheese getCheese();
    }
    
    @Test
    public void shouldUseCurrentlyConfiguredReturnValuesEvenIfTheyChangeAtRuntime() throws Exception {
        HardCodedReturnValues returnsEdam = new HardCodedReturnValues(Cheese.Edam);
        HardCodedReturnValues returnsCheddar = new HardCodedReturnValues(Cheese.Cheddar);
        CheeseShop mockShop = Mockito.mock(CheeseShop.class, new UsingGlobalConfigReturnValues());
        
        ConfigurationSupport.getConfiguration().setReturnValues(returnsEdam);
        
        // shop returns Edam
        assertEquals(Cheese.Edam, mockShop.getCheese());
        
        ConfigurationSupport.getConfiguration().setReturnValues(returnsCheddar);
        
        // same shop now returns Cheddar
        assertEquals(Cheese.Cheddar, mockShop.getCheese());
    }
    
    @After
    public void resetReturnValues() {
        ConfigurationSupport.getConfiguration().resetReturnValues();
    }
}
