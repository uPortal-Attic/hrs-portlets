/**
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
#! /usr/bin/env groovy

/*
 * Groovy script that knows how to download the PeopleSoft standard 3 XSD (req/res/fault) files
 * with good names
 */

def download(address, name) {
    println address + " -> " + name
    def file = new FileOutputStream(name)
    def out = new BufferedOutputStream(file)
    out << new URL(address).openStream()
    out.close()
}

def cleanThem(dir, type) { 
    def pwd = new File(dir)
    println "Deleting *." + type + " in: " + pwd
    pwd.eachFileMatch( ~(".*." + type) ) { f ->
        f.delete()
    }
}

def loadPropConfig(propFile) { 
    def p1 = new java.util.Properties();
    p1.load(new java.io.FileReader(propFile));
    return new ConfigSlurper().parse(p1)
}

cleanThem(".", "xsd");
cleanThem("../../../../test/resources/wsdl/", "wsdl");

def xs = new XmlSlurper();

//Read WSDL locations from app config
def placholders = loadPropConfig('../../ps-placeholder.properties')
def config = loadPropConfig('../../ps-override.properties')
config.keySet().each{ key ->
    config.get((key)).each() { children ->
        def childKey = children.getKey()
        if (childKey == "wsdl") {
            def wsdlTemplate = config.get((key)).get((childKey));
            def wsdlUrl = wsdlTemplate.replaceAll("\\\$\\{peopleSoftURL\\}", placholders.peopleSoftURL);
            def wsdlPath = wsdlUrl.substring(0, wsdlUrl.lastIndexOf("/") + 1);
            def wsdlFullName = wsdlUrl.substring(wsdlUrl.lastIndexOf("/") + 1);
            def wsdlName = wsdlFullName.tokenize(".")[0];
            
            println "Parsing: " + wsdlUrl;
            download(wsdlUrl, "../../../../test/resources/wsdl/" + wsdlFullName);
        
            def wsdl = xs.parse(wsdlUrl);
            download(wsdlPath + wsdl.types.schema.import[0].@schemaLocation, wsdlName + "__REQ.xsd");
            download(wsdlPath + wsdl.types.schema.import[1].@schemaLocation, wsdlName + "__RES.xsd");
            download(wsdlPath + wsdl.types.schema.import[2].@schemaLocation, wsdlName + "__ERR.xsd");
            println "";
      }
    }
} 


