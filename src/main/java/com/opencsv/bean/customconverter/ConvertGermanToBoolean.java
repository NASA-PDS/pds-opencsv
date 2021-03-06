/*
 * Copyright 2016 Andrew Rucker Jones.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opencsv.bean.customconverter;

/**
 * This class converts common German representations of boolean values into a
 * {@link Boolean}.
 * This class also demonstrates how to localize booleans for any other language.
 *
 * @param <T> Type of the bean to be manipulated
 * @param <I> Type of the index into multivalued fields
 * 
 * @author Andrew Rucker Jones
 * @since 3.8
 */
public class ConvertGermanToBoolean<T, I> extends ConverterLanguageToBoolean<T, I> {
    
    private static final String WAHR = "wahr";
    private static final String FALSCH = "falsch";
    private static final String[] TRUE_STRINGS = {WAHR, "ja", "j", "1", "w"};
    private static final String[] FALSE_STRINGS = {FALSCH, "nein", "n", "0", "f"};

    /**
     * Silence code style checker by adding a useless constructor.
     */
    public ConvertGermanToBoolean() {
    }

    @Override
    protected String getLocalizedTrue() { return WAHR; }

    @Override
    protected String getLocalizedFalse() { return FALSCH; }

    @Override
    protected String[] getAllLocalizedTrueValues() { return TRUE_STRINGS; }

    @Override
    protected String[] getAllLocalizedFalseValues() { return FALSE_STRINGS; }
}
