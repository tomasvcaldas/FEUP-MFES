package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class CV {
  private String firstName;
  private String lastName;
  private String email;
  private VDMSet hobbies;
  private VDMSet skills;
  private VDMSet workExperience = SetUtil.set();
  private Object driversLicense;
  private VDMSet education;

  public void cg_init_CV_1(
      final String fName,
      final String lName,
      final String e,
      final Object license,
      final VDMSet edu) {

    workExperience = SetUtil.set();
    skills = SetUtil.set();
    hobbies = SetUtil.set();
    firstName = fName;
    lastName = lName;
    email = e;
    driversLicense = license;
    education = Utils.copy(edu);
  }

  public CV(
      final String fName,
      final String lName,
      final String e,
      final Object license,
      final VDMSet edu) {

    cg_init_CV_1(fName, lName, e, license, Utils.copy(edu));
  }

  public VDMSet getHobbies() {

    return Utils.copy(hobbies);
  }

  public VDMSet getSkills() {

    return Utils.copy(skills);
  }

  public VDMSet getWorkExperience() {

    return Utils.copy(workExperience);
  }

  public Object getDriversLicense() {

    return driversLicense;
  }

  public VDMSet getEducation() {

    return Utils.copy(education);
  }

  public void addPastJob(final PastJob j) {

    workExperience = SetUtil.union(Utils.copy(workExperience), SetUtil.set(j));
  }

  public void removePastJob(final PastJob j) {

    workExperience = SetUtil.diff(Utils.copy(workExperience), SetUtil.set(j));
  }

  public void addHobbie(final String h) {

    hobbies = SetUtil.union(Utils.copy(hobbies), SetUtil.set(h));
  }

  public void removeHobbie(final String h) {

    hobbies = SetUtil.diff(Utils.copy(hobbies), SetUtil.set(h));
  }

  public void addSkill(final String s) {

    skills = SetUtil.union(Utils.copy(skills), SetUtil.set(s));
  }

  public void removeSkill(final String s) {

    skills = SetUtil.diff(Utils.copy(skills), SetUtil.set(s));
  }

  public void addEducation(final Object edu) {

    education = SetUtil.union(Utils.copy(education), SetUtil.set(edu));
  }

  public void setDriversLicense(final Object license) {

    driversLicense = license;
  }

  public CV() {}

  public String toString() {

    return "CV{"
        + "firstName := "
        + Utils.toString(firstName)
        + ", lastName := "
        + Utils.toString(lastName)
        + ", email := "
        + Utils.toString(email)
        + ", hobbies := "
        + Utils.toString(hobbies)
        + ", skills := "
        + Utils.toString(skills)
        + ", workExperience := "
        + Utils.toString(workExperience)
        + ", driversLicense := "
        + Utils.toString(driversLicense)
        + ", education := "
        + Utils.toString(education)
        + "}";
  }
}
