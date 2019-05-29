package com.sauceservice;

import static com.sauceservice.Constants.EMPTY_INPUT;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SauceServiceImplTest {

  @BeforeClass
  public static void setUpClass() {
  }

  @After
  public void setUp() {
  }

  @AfterClass
  public static void cleanUpClass() {
  }

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void testFindAndPrintPermutations()  {
  }

  @Test
  public void testFindAndPrintPermutationsCard() {

  }

  @Test
  public void testValidateInputNull() throws Exception {
    expectedEx.expect(Exception.class);
    expectedEx.expectMessage(EMPTY_INPUT);
  }

}
