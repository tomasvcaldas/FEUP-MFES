package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;
import Hokify.quotes.*;

@SuppressWarnings("all")
public class JobOfferTests extends Tests {
  private void testJobOfferParams() {

    VDMSet allEducations =
        SetUtil.set(
            HighSchoolQuote.getInstance(),
            FineSecondarySchoolQuote.getInstance(),
            TeachingQuote.getInstance(),
            MandatorySchoolQuote.getInstance(),
            UniversityCollegeQuote.getInstance());
    JobOffer jobOffer =
        new JobOffer(
            "Google",
            "CTO",
            1L,
            SetUtil.set(),
            Utils.copy(allEducations),
            CQuote.getInstance());
    assert_(Utils.equals(jobOffer.getCompany(), "Google"));
    assert_(Utils.equals(jobOffer.getPosition(), "CTO"));
    assert_(Utils.equals(jobOffer.getOpenPositions(), 1L));
    assert_(Utils.empty(jobOffer.getNeededSkills()));
    assert_(Utils.equals(jobOffer.getRequiredEducation(), allEducations));
    assert_(Utils.equals(jobOffer.getRequiredDriversLicense(), CQuote.getInstance()));
  }

  private void testAppliances() {

    VDMSet allEducations =
        SetUtil.set(
            HighSchoolQuote.getInstance(),
            FineSecondarySchoolQuote.getInstance(),
            TeachingQuote.getInstance(),
            MandatorySchoolQuote.getInstance(),
            UniversityCollegeQuote.getInstance());
    JobOffer jobOffer =
        new JobOffer(
            "Google",
            "CTO",
            1L,
            SetUtil.set(),
            Utils.copy(allEducations),
            CQuote.getInstance());
    User andre = new User("Andre", "Reis", "andre@gmail.com", 917777777L);
    User tomas = new User("Tomas", "Caldas", "tomas@gmail.com", 919999999L);
    assert_(Utils.empty(jobOffer.getAppliances()));
    jobOffer.addAppliance(andre);
    assert_(Utils.equals(jobOffer.getAppliances(), SetUtil.set(andre)));
    jobOffer.addAppliance(tomas);
    assert_(Utils.equals(jobOffer.getAppliances(), SetUtil.set(andre, tomas)));
    jobOffer.removeAppliance(andre);
    assert_(Utils.equals(jobOffer.getAppliances(), SetUtil.set(tomas)));
  }

  public void runTests() {

    testJobOfferParams();
    testAppliances();
  }

  public JobOfferTests() {}

  public String toString() {

    return "JobOfferTests{}";
  }
}
