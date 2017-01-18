package com.github.springtestdbunit.sample.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.sample.entity.Person;
import com.github.springtestdbunit.sample.entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ProductService productService;

    @Test
    @DatabaseSetup("sampleData.xml")
    public void testFind() throws Exception {
        List<Person> personList = this.personService.find("hil");
        assertEquals(1, personList.size());
        assertEquals("Phillip", personList.get(0).getFirstName());
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    @ExpectedDatabase(value = "expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception {
        this.personService.remove(1);
    }

    @Test
    @DatabaseSetup("sampleData1.xml")
    public void tesNoValueLastName() throws Exception {
        List<Person> personList = this.personService.find("bl");
        assertEquals(1, personList.size());
        assertEquals("blue", personList.get(0).getFirstName());
        assertEquals("poet", personList.get(0).getLastName());
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    @DatabaseSetup("ProductInitData.xml")
//    @ExpectedDatabase(value = "expectedProductData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT) // verify error!
    @ExpectedDatabase(value = "ProductInitData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @ExpectedDatabase(value = "sampleData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testVerifyMultiTableValues() throws Exception {
//        this.personService.remove(1);
//        this.productService.remove(2);
    }
}
