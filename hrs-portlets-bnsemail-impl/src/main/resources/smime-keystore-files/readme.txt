====
    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.
====

# Find the alias of the key in the p12 file
keytool -list -keystore OHRPayRoll.p12 -storetype PKCS12

# Create an empty keystore
keytool -genkey -alias temp -keystore smime-keystore.jks
keytool -delete -alias temp -keystore smime-keystore.jks

# Import the signing key into the keystore
keytool -v -importkeystore -srckeystore OHRPayRoll.p12 -srcstoretype PKCS12 -destkeystore smime-keystore.jks -deststoretype JKS -destalias ohr-smime -alias "cn=uw madison ohr payroll,ou=madison,o=university of wisconsin,st=wisconsin,c=us signing and decryption key"

# This Java helper class was used instead of the previous command to work-around a problem with the alias in the
# original p12 file. The alias included a null character at the end of the string which was impossible to specify
# on the command line
public class Import {
    public static void main(String[] args) throws Exception {
        sun.security.tools.KeyTool.main(new String[] {
            "-v", 
            "-importkeystore", 
            "-srckeystore", "OHRPayRoll.p12",
            "-srcstoretype", "PKCS12", 
            "-destkeystore", "smime-keystore.jks", 
            "-deststoretype", "JKS", 
            "-deststorepass", "--------",
            "-destkeypass", "--------",
            "-destalias", "ohr-smime", 
            "-alias", "cn=uw madison ohr payroll,ou=madison,o=university of wisconsin,st=wisconsin,c=us signing and decryption key" + (char)0
        });
    }
}

# These certs were exported from Thunderbird 3.1.8 after viewing an email signed by the OHR certificate  
keytool -v -import -keystore smime-keystore.jks -alias 1_Entrust.netSecureServerCertificationAuthority -trustcacerts -file 1_Entrust.netSecureServerCertificationAuthority.der -deststorepass --------
keytool -v -import -keystore smime-keystore.jks -alias 2_Entrust.netCertificationAuthority_2048 -trustcacerts -file 2_Entrust.netCertificationAuthority_2048.der -deststorepass --------
keytool -v -import -keystore smime-keystore.jks -alias 3_EntrustManagedServicesCommercialPublicRootCA -trustcacerts -file 3_EntrustManagedServicesCommercialPublicRootCA.der -deststorepass --------
keytool -v -import -keystore smime-keystore.jks -alias 4_EntrustEducationSharedServiceProvider -trustcacerts -file 4_EntrustEducationSharedServiceProvider.der -deststorepass --------

# Used to verify that the keystore contains 5 certs
keytool -list -keystore smime-keystore.jks -storepass --------
 