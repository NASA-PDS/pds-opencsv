package com.opencsv.bean;

import com.opencsv.bean.mocks.ignore.NonSerial;
import com.opencsv.bean.mocks.ignore.Serial;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IgnoreTest {

    @Test
    public void testSerialVersionUIDNonSerializable() throws CsvException {
        NonSerial bean = new NonSerial(2, "3");
        StringWriter w = new StringWriter();
        StatefulBeanToCsv<NonSerial> btcsv = new StatefulBeanToCsvBuilder<NonSerial>(w)
                .withApplyQuotesToAll(false)
                .build();
        btcsv.write(bean);
        assertEquals("SERIALVERSIONUID,TESTINT,TESTSTRING\n1,2,3\n", w.toString());
    }

    @Test
    public void testSerialVersionUIDSerializable() throws CsvException {
        Serial bean = new Serial(3, "4");
        StringWriter w = new StringWriter();
        StatefulBeanToCsv<Serial> btcsv = new StatefulBeanToCsvBuilder<Serial>(w)
                .withApplyQuotesToAll(false)
                .build();
        btcsv.write(bean);
        assertEquals("TESTINT,TESTSTRING\n3,4\n", w.toString());
    }
}
