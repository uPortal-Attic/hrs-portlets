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

package edu.wisc.hr.demo;

import edu.wisc.hr.dao.bnsumm.BenefitSummaryDao;
import edu.wisc.hr.demo.support.RandomNameGenerator;
import edu.wisc.hr.dm.bnsumm.Benefit;
import edu.wisc.hr.dm.bnsumm.BenefitSummary;
import edu.wisc.hr.dm.bnsumm.Dependent;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * BenefitSummaryDao implementation that generates random dependents and benefits.
 */
@Repository
public class RandomBenefitSummaryDao
    implements BenefitSummaryDao {

    private Map<String, BenefitSummary> emplIdToBenefitSummary = new HashMap<String, BenefitSummary>();

    private Random random = new Random();

    private static final RandomNameGenerator RANDOM_NAME_GENERATOR = new RandomNameGenerator();

    // benefits with variable coverage where coverage can range from
    // "Self", "Self + Spouse / Domestic Partner", "Self + Dependent Children", and "Family"
    private static final String[] VARIABLE_COVERAGE_FAMILY_BENEFITS = {
         "State Group Health",
         "EPIC Benefits+",
         "State Dental",
         "VSP Vision Insurance",
         "Orthodontia Insurance"
    };

    // benefits that only make sense to Elect if you have a partner,
    // where the "coverage" is either "Elect" or "Waive".
    private static final String[] ELECT_OR_WAIVE_IF_PARTNER_BENEFITS = {
        "State Group Life - Spouse / Domestic Partner",
        "Individual & Family Life - Spouse / Domestic Partner"
    };

    // benefits that only make sense to Elect if you have dependent children,
    // where the "coverage" is either "Elect or "Waive"
    private static final String[] ELECT_OR_WAIVE_IF_CHILDREN_BENEFITS = {
         "State Group Life - Dependents",
         "Individual & Family Life - Child(ren)"
    };

    // Benefits in order of layers such that it only makes sense to have later items in the series if you elected
    // earlier items in the series.
    private static final String[] LAYERED_BENEFITS = {
        "State Group Life - Basic",
        "State Group Life - Supplemental",
        "State Group Life - Additional"
    };

    private static final String[] ELECT_OR_WAIVE_BENEFITS = {
            "Individual & Family Life - Employee",
            "UW Employees Inc Life",
            "AD&D (Accidental Death and Dismemberment)",
            "Income Continuation Insurance",
            "ERA Medical",
            "ERA Dependant Care",
            "Retirement System",
            "Deferred Savings Plan",
            "Long Term Care Insurance",
            "Umbrella Liability Insurance",
            "Pet Insurance",
            "Long Term Disability",
            "Short Term Disability",
            "Medium Term Disability",
            "Flood insurance",
            "Repetitive Stress Injury Insurance",
            "Hearing Loss Insurance",
            "Locksmith Services",
            "Rental Car Insurance",
            "Roadside Assistance",
            "Identity Theft Insurance",
            "Pet Savings Plan",
            "Malware Insurance",
            "Thrift Savings Plan",
            "AppleCare",
            "VIP Technical Support",
            "Home Internet Reimbursement",
            "Employer-Provided Cell Phone",
            "Gym Membership",
            "Library Circulation Privileges",
            "Meal Plan",
            "Employee Assistance Services",
            "Medical Evacuation Insurance"

    };

    private static final String[] COVERAGES = {
            "Waive", "Elect"
    };

    private static final String SPOUSE_RELATIONSHIP = "Spouse";

    private static final String DOMESTIC_PARTNER_RELATIONSHIP = "Domestic Partner";

    private static final String CHILD_RELATIONSHIP = "Child Tax Dependent";


    private static final String FAMILY_COVERAGE = "Family";

    private static final String SELF_AND_SPOUSE_OR_PARTNER_COVERAGE = "Self & Spouse / Domestic Partner";

    private static final String SELF_AND_CHILDREN_COVERAGE = "Self & Dependent Children";

    private static final String SELF_COVERAGE = "Self";

    private static final String WAIVE_COVERAGE = "Waive";

    private static final String ElECT_COVERAGE = "Elect";


    @Override
    public BenefitSummary getBenefitSummary(String emplId) {

        if (emplIdToBenefitSummary.containsKey(emplId)) {
            return (BenefitSummary) emplIdToBenefitSummary.get(emplId);
        }

        BenefitSummary benefitSummary = new BenefitSummary();

        benefitSummary.setEnrollmentFlag("WhatsAnEnrollmentFlag?");

        // generate dependents

        List<Dependent> dependents = benefitSummary.getDependents();

        dependents.addAll(randomDependents(emplId));

        boolean havePartner = dependentsContainsSpouseOrDomesticPartner(dependents);
        boolean haveChildren = dependentsContainsChild(dependents);

        List<Benefit> benefits = benefitSummary.getBenefits();

        // generate benefits that are conditioned on dependents
        for (String variableCoverageBenefitName : VARIABLE_COVERAGE_FAMILY_BENEFITS) {

            // don't exclude completely any of these benefits because there's so many election modes to demo
            Benefit benefit = new Benefit();
            benefit.setName(variableCoverageBenefitName);

            if (havePartner && random.nextBoolean()) {

                if (haveChildren && random.nextBoolean()) {
                    benefit.setCoverage(FAMILY_COVERAGE);
                } else {
                    benefit.setCoverage(SELF_AND_SPOUSE_OR_PARTNER_COVERAGE);
                }


            } else if (haveChildren && random.nextBoolean() ) {

                benefit.setCoverage(SELF_AND_CHILDREN_COVERAGE);

            } else if (random.nextBoolean()) {

                benefit.setCoverage(SELF_COVERAGE);
            } else {
                benefit.setCoverage(WAIVE_COVERAGE);
            }

            benefits.add(benefit);
        }

        // generate benefits conditioned on spouse or partner
        for (String partnerBenefitName : ELECT_OR_WAIVE_IF_PARTNER_BENEFITS) {
            // ignore half the benefits
            if (random.nextBoolean()) {
                Benefit benefit = new Benefit();
                benefit.setName(partnerBenefitName);

                // elect half the eligible benefits, waive the rest
                if (random.nextBoolean() && havePartner) {
                    benefit.setCoverage(ElECT_COVERAGE);
                } else {
                    benefit.setCoverage(WAIVE_COVERAGE);
                }

                benefits.add(benefit);
            }
        }

        // generate benefits conditioned on children
        for (String childBenefitName : ELECT_OR_WAIVE_IF_CHILDREN_BENEFITS) {

            // ignore half the benefits
            if (random.nextBoolean()) {
                Benefit benefit = new Benefit();
                benefit.setName(childBenefitName);

                // elect half the eligible benefits, waive the rest
                if (random.nextBoolean() && haveChildren) {
                    benefit.setCoverage(ElECT_COVERAGE);
                } else {
                    benefit.setCoverage(WAIVE_COVERAGE);
                }

                benefits.add(benefit);
            }
        }

        // generate layered life insurance benefit
        boolean electMoreBenefits = true;
        for (String layeredBenefitName : LAYERED_BENEFITS) {
            Benefit benefit = new Benefit();
            benefit.setName(layeredBenefitName);

            // electing this benefit is an option only if the previous were elected
            electMoreBenefits = electMoreBenefits && random.nextBoolean();

            if (electMoreBenefits) {
                benefit.setCoverage(ElECT_COVERAGE);
            } else {
                benefit.setCoverage(WAIVE_COVERAGE);
            }

            benefits.add(benefit);

        }


        // generate other random take-or-leave benefits
        for (String benefitName : ELECT_OR_WAIVE_BENEFITS) {

            // ignore half
            if (random.nextBoolean()) {
                Benefit benefit = new Benefit();
                benefit.setName(benefitName);

                if (random.nextBoolean()) {
                    benefit.setCoverage(ElECT_COVERAGE);
                } else {
                    benefit.setCoverage(WAIVE_COVERAGE);
                }

                benefits.add(benefit);
            }


        }

        this.emplIdToBenefitSummary.put(emplId, benefitSummary);

        return benefitSummary;
    }


    List<Dependent> randomDependents(String emplId) {

        List<Dependent> dependents = new LinkedList<Dependent>();

        // Spouse or domestic partner?

        if (random.nextBoolean()) {
            // spouse

            // TODO: make the spouse's last name likely to match the employee's

            Dependent spouse = new Dependent();
            spouse.setName(RANDOM_NAME_GENERATOR.randomName());
            spouse.setRelationship("Spouse");

            dependents.add(spouse);

        } else if (random.nextBoolean()) {
            // domestic partner

            Dependent domesticPartner = new Dependent();


            if (random.nextBoolean()) {
                // partner's last name matches employee's
                domesticPartner.setName(RANDOM_NAME_GENERATOR.randomName(emplId));
            } else {
                domesticPartner.setName(RANDOM_NAME_GENERATOR.randomName());
            }


            domesticPartner.setRelationship(DOMESTIC_PARTNER_RELATIONSHIP);

            dependents.add(domesticPartner);
        }


        // Children?

        while (random.nextBoolean()) {
            // add a child

            // TODO: make the child's last name likely to match the employee's

            Dependent child = new Dependent();

            if (random.nextBoolean()) {
                child.setName(RANDOM_NAME_GENERATOR.randomName(emplId));
            } else {
                child.setName(RANDOM_NAME_GENERATOR.randomName());
            }

            child.setRelationship(CHILD_RELATIONSHIP);
            dependents.add(child);
        }


        return dependents;
    }

    private boolean dependentsContainsSpouseOrDomesticPartner(Collection<Dependent> dependents) {
        for (Dependent dependent : dependents) {
            if (dependent.getRelationship().equals(SPOUSE_RELATIONSHIP)) return true;
            if (dependent.getRelationship().equals(DOMESTIC_PARTNER_RELATIONSHIP)) return true;
        }

        return false;

    }

    private boolean dependentsContainsChild(Collection<Dependent> dependents) {
        for (Dependent dependent : dependents) {
            if (dependent.getRelationship().equals(CHILD_RELATIONSHIP)) return true;
        }

        return false;
    }


}
