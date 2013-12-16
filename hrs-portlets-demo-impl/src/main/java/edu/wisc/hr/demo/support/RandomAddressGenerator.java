/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.wisc.hr.demo.support;

import edu.wisc.hr.dm.person.HomeAddress;
import edu.wisc.hr.dm.person.OfficeAddress;

import java.util.Random;

/**
 * Random address generator.
 * Stateless apart from its use of a java.util.Random.
 */
public class RandomAddressGenerator {

    private Random random = new Random();

    private static final String[] STATE_ABBREVIATIONS = {
         "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
         "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
         "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
         "WI", "WY",
         "AS", "DC", "FM", "GU", "MH", "MP", "PW", "PR", "VI",
         "AE", "AA", "AP" };

    private static final String[] CITIES = {
            "Gilbert", "Greenville", "Hull", "Lincoln", "Madison", "Middleton", "Tempe", "Plymouth", "Verona"
    };

    private static final String[] BUILDING_NAMES = {
            "Astor", "Branford", "Calhoun", "Carnegie", "Clark", "Cooke", "Crocker", "Davenport", "Drew", "Duke",
            "Gryffindor", "Hogwarts", "Hufflepuff", "Jonathan Edwards", "Marshall Field", "Gould",
            "Hill", "Hopkins", "Huntington", "Mellon", "Morgan", "Nobel", "Ravenclaw", "Rockfeller", "Schwab",
            "Silliman","Slytherin", "Vanderbilt","Yerkes"
    };

    private static final String[] BUILDING_TYPES = {
            "College", "Dormitory", "Estate", "Hall", "Hill", "Observatory", "Residence", "School",
    };

    private static final String[] CARDINALS = { "N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    private static final String[] STREET_NAMES = { "Parkview", "Tall Tree", "Johnson", "Dayton", "Nopal",
            "Riveredge", "Mathews" };

    private static final String[] STREET_TYPES = {
            "Avenue", "Lane", "Path", "Road", "Street", "Trail",
    };

    public HomeAddress randomHomeAddress() {
        HomeAddress address = new HomeAddress();

        address.setMailDrop( randomMailDrop() );

        address.setAddress1( randomStreetAddress() );

        if (random.nextBoolean()) {
            address.setAddress2( randomNamedBuilding());

            if (random.nextBoolean()) {
                address.setAddress3( "care of James Gosling");
            }
        }



        address.setCity( randomCity() );

        // What's a location???
        address.setLocation("Location?");

        address.setPrimaryPhone( randomPhoneNumber() );

        address.setRoomNumber( Integer.toString(random.nextInt(9000)) );

        address.setState( randomStateAbbreviation() );

        address.setZip( Integer.toString( random.nextInt(80000) + 10000 ) );

        address.setReleaseHomeAddress( random.nextBoolean());

        return address;
    }



    /**
     *
     * If the department is supplied (non-null), half the time it will be included in the address.
     *
     * @param department office department, or null
     * @return
     */
    public OfficeAddress randomOfficeAddress(String department) {

        OfficeAddress address = new OfficeAddress();

        address.setMailDrop( randomMailDrop() );

        address.setAddress1( randomStreetAddress() );

        if (random.nextBoolean() && department != null) {
            address.setAddress2(department);

            if (random.nextBoolean()) {
                address.setAddress3( randomNamedBuilding() );
            }

        } else if (random.nextBoolean()) {
            address.setAddress2( randomNamedBuilding());
        }

        address.setCity( randomCity() );

        address.setLocation("Location?");

        address.setPrimaryPhone( randomPhoneNumber() );

        address.setRoomNumber( Integer.toString(random.nextInt(9000)) );

        address.setState( randomStateAbbreviation() );

        address.setZip( Integer.toString( random.nextInt(80000) + 10000 ) );

        address.setOtherPhone( randomPhoneNumber() );

        return address;
    }

    public String randomMailDrop() {

        int dropNumber = random.nextInt(1000);
        return "Mail Drop ".concat(Integer.toString(dropNumber));

    }

    public String randomStateAbbreviation() {
        return STATE_ABBREVIATIONS[ random.nextInt( STATE_ABBREVIATIONS.length ) ];
    }

    public String randomCity() {
        return CITIES[ random.nextInt( CITIES.length ) ];
    }

    public String randomPhoneNumber() {

        int areaCode = random.nextInt(800) + 100;

        int regionCode = random.nextInt(800) + 100;

        int stationCode = random.nextInt(8000) + 1000;

        return String.format("(%d) %d-%d", areaCode, regionCode, stationCode);

    }

    public String randomNamedBuilding() {

        String properName = BUILDING_NAMES[ random.nextInt( BUILDING_NAMES.length) ];
        String buildingType = BUILDING_TYPES[ random.nextInt( BUILDING_TYPES.length) ];

        return properName.concat(" ").concat(buildingType);

    }

    public String randomStreetAddress() {

        int addressNumber = random.nextInt(1000);

        String direction = CARDINALS[ random.nextInt( CARDINALS.length) ];

        String streetName = STREET_NAMES[ random.nextInt( STREET_NAMES.length) ];

        String streetType = STREET_TYPES[ random.nextInt( STREET_TYPES.length) ];

        String address = Integer.toString(addressNumber).concat(" ");

        if (random.nextBoolean()) {
            // include the cardinal
            address = address.concat(direction).concat(" ");
        }

        address = address.concat(streetName).concat(" ");
        address = address.concat(streetType);

        return address;
    }
}
