package Hokify;

import java.util.*;

import Hokify.quotes.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class HokifyTests extends Tests {
  private User andre = new User("Andre", "Reis", "andre@gmail.com", 917777777L);
  private User tomas = new User("Tomas", "Caldas", "tomas@gmail.com", 919999999L);
  private VDMSet allEducations =
      SetUtil.set(
          HighSchoolQuote.getInstance(),
          FineSecondarySchoolQuote.getInstance(),
          TeachingQuote.getInstance(),
          MandatorySchoolQuote.getInstance(),
          UniversityCollegeQuote.getInstance());
  private JobOffer googleOffer =
      new JobOffer(
          "Google",
          "CTO",
          1L,
          SetUtil.set("Java"),
          SetUtil.set(),
          CQuote.getInstance());
  private JobOffer facebookOffer =
      new JobOffer(
          "Facebook",
          "CodeMonkey",
          1L,
          SetUtil.set("C"),
          SetUtil.set(HighSchoolQuote.getInstance()),
          null);

  private void testHokify() {

    Hokify hokify = new Hokify();
    assert_(Utils.empty(hokify.getUsers()));
    assert_(Utils.empty(hokify.getJobOffers()));
  }

  private void testUsers() {

    Hokify hokify = new Hokify();
    hokify.addUser(andre);
    assert_(Utils.equals(hokify.getUsers(), SetUtil.set(andre)));
    hokify.addUser(tomas);
    assert_(Utils.equals(hokify.getUsers(), SetUtil.set(andre, tomas)));
    hokify.removeUser(andre);
    assert_(Utils.equals(hokify.getUsers(), SetUtil.set(tomas)));
    hokify.removeUser(tomas);
    assert_(Utils.empty(hokify.getUsers()));
  }

  private void testJobOffers() {

    Hokify hokify = new Hokify();
    hokify.addJobOffer(googleOffer);
    assert_(Utils.equals(hokify.getJobOffers(), SetUtil.set(googleOffer)));
    hokify.addJobOffer(facebookOffer);
    assert_(Utils.equals(hokify.getJobOffers(), SetUtil.set(facebookOffer, googleOffer)));
    hokify.removeJobOffer(facebookOffer);
    assert_(Utils.equals(hokify.getJobOffers(), SetUtil.set(googleOffer)));
    hokify.removeJobOffer(googleOffer);
    assert_(Utils.empty(hokify.getJobOffers()));
  }

  private void testMatching() {

    Hokify hokify = new Hokify();
    andre.createCV();
    tomas.createCV();
    andre.setDriversLicense(CQuote.getInstance());
    tomas.addEducation(HighSchoolQuote.getInstance());
    hokify.addUser(andre);
    hokify.addUser(tomas);
    hokify.addJobOffer(googleOffer);
    hokify.addJobOffer(facebookOffer);
    assert_(Utils.empty(hokify.getMatchingOffersForUser(andre)));
    assert_(Utils.empty(hokify.getMatchingOffersForUser(tomas)));
    andre.addSkill("Java");
    assert_(Utils.equals(hokify.getMatchingOffersForUser(andre), SetUtil.set(googleOffer)));
    assert_(Utils.equals(hokify.getMatchingUsersForOffer(googleOffer), SetUtil.set(andre)));
    tomas.addSkill("C");
    assert_(Utils.equals(hokify.getMatchingOffersForUser(tomas), SetUtil.set(facebookOffer)));
    assert_(Utils.equals(hokify.getMatchingUsersForOffer(facebookOffer), SetUtil.set(tomas)));
    andre.addSkill("C");
    andre.addEducation(HighSchoolQuote.getInstance());
    assert_(
        Utils.equals(
            hokify.getMatchingOffersForUser(andre), SetUtil.set(googleOffer, facebookOffer)));
    assert_(
        Utils.equals(hokify.getMatchingUsersForOffer(facebookOffer), SetUtil.set(andre, tomas)));
  }

  private void testAppliance() {

    Hokify hokify = new Hokify();
    Number googleOpenPos = googleOffer.getOpenPositions();
    assert_(Utils.empty(googleOffer.getAppliances()));
    hokify.addAppliance(andre, googleOffer);
    assert_(Utils.equals(googleOffer.getAppliances(), SetUtil.set(andre)));
    assert_(Utils.equals(andre.getAppliances(), SetUtil.set(googleOffer)));
    hokify.addAppliance(tomas, googleOffer);
    assert_(Utils.equals(googleOffer.getAppliances(), SetUtil.set(andre, tomas)));
    assert_(Utils.equals(tomas.getAppliances(), SetUtil.set(googleOffer)));
    hokify.acceptAppliance(andre, googleOffer);
    assert_(Utils.equals(googleOffer.getOpenPositions(), googleOpenPos.longValue() - 1L));
    assert_(Utils.equals(googleOffer.getAppliances(), SetUtil.set(tomas)));
    assert_(Utils.empty(andre.getAppliances()));
    Boolean existsExpResult_11 = false;
    VDMSet set_11 = andre.getCV().getWorkExperience();
    for (Iterator iterator_11 = set_11.iterator();
        iterator_11.hasNext() && !(existsExpResult_11);
        ) {
      PastJob job = ((PastJob) iterator_11.next());
      existsExpResult_11 =
          PastJob.areEqual(
              job,
              new PastJob(
                  googleOffer.getCompany(),
                  googleOffer.getPosition(),
                  new Job.Date(2018L, 1L, 3L),
                  null));
    }
    assert_(existsExpResult_11);

    googleOpenPos = googleOffer.getOpenPositions();
    hokify.declineAppliance(tomas, googleOffer);
    assert_(Utils.equals(googleOffer.getOpenPositions(), googleOpenPos));
    assert_(Utils.empty(googleOffer.getAppliances()));
    assert_(Utils.empty(tomas.getAppliances()));
  }

  public void runTests() {

    testHokify();
    testUsers();
    testJobOffers();
    testMatching();
    testAppliance();
  }

  public HokifyTests() {}

  public String toString() {

    return "HokifyTests{"
        + "andre := "
        + Utils.toString(andre)
        + ", tomas := "
        + Utils.toString(tomas)
        + ", allEducations := "
        + Utils.toString(allEducations)
        + ", googleOffer := "
        + Utils.toString(googleOffer)
        + ", facebookOffer := "
        + Utils.toString(facebookOffer)
        + "}";
  }
}
