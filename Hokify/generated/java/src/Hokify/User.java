package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class User {
  private String firstName;
  private String lastName;
  private String email;
  private Number phonenumber;
  private CV curriculumVitae = null;
  private VDMSet appliances;

  public void cg_init_User_1(
      final String fName, final String lName, final String e, final Number p) {

    firstName = fName;
    lastName = lName;
    email = e;
    phonenumber = p;
    appliances = SetUtil.set();
  }

  public User(final String fName, final String lName, final String e, final Number p) {

    cg_init_User_1(fName, lName, e, p);
  }

  public void createCV() {

    curriculumVitae = new CV(firstName, lastName, email, null, SetUtil.set());
  }

  public Boolean offerMatches(final JobOffer offer) {

    Boolean andResult_19 = false;

    Boolean orResult_7 = false;

    if (Utils.equals(offer.getRequiredDriversLicense(), null)) {
      orResult_7 = true;
    } else {
      orResult_7 =
          Utils.equals(curriculumVitae.getDriversLicense(), offer.getRequiredDriversLicense());
    }

    if (orResult_7) {
      Boolean andResult_20 = false;

      Boolean orResult_8 = false;

      if (Utils.empty(offer.getRequiredEducation())) {
        orResult_8 = true;
      } else {
        orResult_8 =
            !(Utils.empty(
                SetUtil.intersect(offer.getRequiredEducation(), curriculumVitae.getEducation())));
      }

      if (orResult_8) {
        Boolean orResult_9 = false;

        if (Utils.empty(offer.getNeededSkills())) {
          orResult_9 = true;
        } else {
          orResult_9 =
              Utils.divide(
                      (1.0
                          * SetUtil.intersect(curriculumVitae.getSkills(), offer.getNeededSkills())
                              .size()),
                      offer.getNeededSkills().size())
                  > 0.5;
        }

        if (orResult_9) {
          andResult_20 = true;
        }
      }

      if (andResult_20) {
        andResult_19 = true;
      }
    }

    return andResult_19;
  }

  public VDMSet getMatchingOffers(final VDMSet offers) {

    VDMSet validJobs = SetUtil.set();
    for (Iterator iterator_17 = offers.iterator(); iterator_17.hasNext(); ) {
      JobOffer offer = (JobOffer) iterator_17.next();
      if (offerMatches(offer)) {
        validJobs = SetUtil.union(Utils.copy(validJobs), SetUtil.set(offer));
      }
    }
    return Utils.copy(validJobs);
  }

  public String getFirstName() {

    return firstName;
  }

  public String getLastName() {

    return lastName;
  }

  public String getEmail() {

    return email;
  }

  public Number getPhoneNumber() {

    return phonenumber;
  }

  public CV getCV() {

    return curriculumVitae;
  }

  public VDMSet getAppliances() {

    return Utils.copy(appliances);
  }

  public void addPastJob(final PastJob j) {

    curriculumVitae.addPastJob(j);
  }

  public void removePastJob(final PastJob j) {

    curriculumVitae.removePastJob(j);
  }

  public void addSkill(final String s) {

    curriculumVitae.addSkill(s);
  }

  public void removeSkill(final String s) {

    curriculumVitae.removeSkill(s);
  }

  public void addHobbie(final String h) {

    curriculumVitae.addHobbie(h);
  }

  public void removeHobbie(final String h) {

    curriculumVitae.removeHobbie(h);
  }

  public void setDriversLicense(final Object license) {

    curriculumVitae.setDriversLicense(license);
  }

  public void addEducation(final Object edu) {

    curriculumVitae.addEducation(edu);
  }

  public void applyForJob(final JobOffer j) {

    appliances = SetUtil.union(Utils.copy(appliances), SetUtil.set(j));
    j.addAppliance(this);
  }

  public void declinedJob(final JobOffer job) {

    appliances = SetUtil.diff(Utils.copy(appliances), SetUtil.set(job));
  }

  public void acceptedJob(final JobOffer job) {

    appliances = SetUtil.diff(Utils.copy(appliances), SetUtil.set(job));
    curriculumVitae.addPastJob(
        new PastJob(job.getCompany(), job.getPosition(), new Job.Date(2018L, 1L, 3L), null));
  }

  public User() {}

  public static Boolean areEqual(final User u1, final User u2) {

    return Utils.equals(u1.email, u2.email);
  }

  public String toString() {

    return "User{"
        + "firstName := "
        + Utils.toString(firstName)
        + ", lastName := "
        + Utils.toString(lastName)
        + ", email := "
        + Utils.toString(email)
        + ", phonenumber := "
        + Utils.toString(phonenumber)
        + ", curriculumVitae := "
        + Utils.toString(curriculumVitae)
        + "}";
  }
}
