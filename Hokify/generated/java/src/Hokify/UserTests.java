package Hokify;

import java.util.*;

import Hokify.quotes.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class UserTests extends Tests {
  private void testUserParams() {

    User u = new User("Tomas", "Caldas", "tomasvcaldas@gmail.com", 925302024L);
    assert_(Utils.equals(u.getFirstName(), "Tomas"));
    assert_(Utils.equals(u.getLastName(), "Caldas"));
    assert_(Utils.equals(u.getEmail(), "tomasvcaldas@gmail.com"));
    assert_(Utils.equals(u.getPhoneNumber(), 925302024L));
  }

  private void testUserCV() {

    User u = new User("Tomas", "Caldas", "tomasvcaldas@gmail.com", 925302024L);
    PastJob googleJob =
        new PastJob("Google", "CEO", new Job.Date(2007L, 3L, 12L), new Job.Date(2007L, 9L, 12L));
    PastJob facebookJob =
        new PastJob(
            "Facebook", "Tester", new Job.Date(2008L, 3L, 12L), new Job.Date(2008L, 3L, 31L));
    u.createCV();
    assert_(Utils.empty(u.getCV().getHobbies()));
    assert_(Utils.empty(u.getCV().getSkills()));
    assert_(Utils.empty(u.getCV().getWorkExperience()));
    assert_(Utils.equals(googleJob.getCompany(), "Google"));
    assert_(Utils.equals(googleJob.getPosition(), "CEO"));
    assert_(Utils.equals(googleJob.getStartDate(), new Job.Date(2007L, 3L, 12L)));
    assert_(Utils.equals(googleJob.getEndDate(), new Job.Date(2007L, 9L, 12L)));
    assert_(Utils.equals(facebookJob.getCompany(), "Facebook"));
    assert_(Utils.equals(facebookJob.getPosition(), "Tester"));
    assert_(Utils.equals(facebookJob.getStartDate(), new Job.Date(2008L, 3L, 12L)));
    assert_(Utils.equals(facebookJob.getEndDate(), new Job.Date(2008L, 3L, 31L)));
    u.addPastJob(googleJob);
    assert_(Utils.equals(u.getCV().getWorkExperience(), SetUtil.set(googleJob)));
    u.addPastJob(facebookJob);
    assert_(Utils.equals(u.getCV().getWorkExperience(), SetUtil.set(googleJob, facebookJob)));
    u.removePastJob(googleJob);
    assert_(Utils.equals(u.getCV().getWorkExperience(), SetUtil.set(facebookJob)));
    u.removePastJob(facebookJob);
    assert_(Utils.empty(u.getCV().getWorkExperience()));
    u.addHobbie("Drawing");
    assert_(Utils.equals(u.getCV().getHobbies(), SetUtil.set("Drawing")));
    u.addHobbie("Painting");
    assert_(Utils.equals(u.getCV().getHobbies(), SetUtil.set("Drawing", "Painting")));
    u.removeHobbie("Painting");
    assert_(Utils.equals(u.getCV().getHobbies(), SetUtil.set("Drawing")));
    u.removeHobbie("Drawing");
    assert_(Utils.empty(u.getCV().getHobbies()));
    u.addSkill("Java");
    assert_(Utils.equals(u.getCV().getSkills(), SetUtil.set("Java")));
    u.addSkill("C#");
    assert_(Utils.equals(u.getCV().getSkills(), SetUtil.set("Java", "C#")));
    u.removeSkill("Java");
    assert_(Utils.equals(u.getCV().getSkills(), SetUtil.set("C#")));
    u.removeSkill("C#");
    assert_(Utils.empty(u.getCV().getSkills()));
    u.setDriversLicense(CQuote.getInstance());
    assert_(Utils.equals(u.getCV().getDriversLicense(), CQuote.getInstance()));
    u.addEducation(UniversityCollegeQuote.getInstance());
    assert_(
        Utils.equals(
            u.getCV().getEducation(),
            SetUtil.set(UniversityCollegeQuote.getInstance())));
  }

  public void runTests() {

    testUserParams();
    testUserCV();
  }

  public UserTests() {}

  public String toString() {

    return "UserTests{}";
  }
}
