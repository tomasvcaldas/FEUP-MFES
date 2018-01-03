package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class JobOffer extends Job {
  private Number openPositions;
  private VDMSet appliances;
  private VDMSet neededSkills;
  private VDMSet requiredEducation;
  private Object requiredDriversLicense;

  public void cg_init_JobOffer_1(
      final String c,
      final String p,
      final Number openPos,
      final VDMSet skills,
      final VDMSet reqEdu,
      final Object reqLicense) {

    openPositions = openPos;
    appliances = SetUtil.set();
    neededSkills = Utils.copy(skills);
    requiredEducation = Utils.copy(reqEdu);
    requiredDriversLicense = reqLicense;
    cg_init_Job_1(c, p);
  }

  public JobOffer(
      final String c,
      final String p,
      final Number openPos,
      final VDMSet skills,
      final VDMSet reqEdu,
      final Object reqLicense) {

    cg_init_JobOffer_1(c, p, openPos, Utils.copy(skills), Utils.copy(reqEdu), reqLicense);
  }

  public Number getOpenPositions() {

    return openPositions;
  }

  public VDMSet getAppliances() {

    return Utils.copy(appliances);
  }

  public VDMSet getNeededSkills() {

    return Utils.copy(neededSkills);
  }

  public VDMSet getRequiredEducation() {

    return Utils.copy(requiredEducation);
  }

  public Object getRequiredDriversLicense() {

    return requiredDriversLicense;
  }

  public void addAppliance(final User user) {

    appliances = SetUtil.union(Utils.copy(appliances), SetUtil.set(user));
  }

  public void removeAppliance(final User user) {

    appliances = SetUtil.diff(Utils.copy(appliances), SetUtil.set(user));
  }

  public VDMSet getMatchingOffers(final VDMSet users) {

    VDMSet validCandidates = SetUtil.set();
    for (Iterator iterator_16 = users.iterator(); iterator_16.hasNext(); ) {
      User user = (User) iterator_16.next();
      Boolean orResult_3 = false;

      if (Utils.empty(neededSkills)) {
        orResult_3 = true;
      } else {
        Boolean andResult_8 = false;

        if (!(Utils.equals(user.getCV(), null))) {
          if (Utils.divide(
                  (1.0
                      * SetUtil.intersect(user.getCV().getSkills(), Utils.copy(neededSkills))
                          .size()),
                  neededSkills.size())
              > 0.5) {
            andResult_8 = true;
          }
        }

        orResult_3 = andResult_8;
      }

      if (orResult_3) {
        validCandidates = SetUtil.union(Utils.copy(validCandidates), SetUtil.set(user));
      }
    }
    return Utils.copy(validCandidates);
  }

  public void acceptAppliance(final User u) {

    removeAppliance(u);
    openPositions = openPositions.longValue() - 1L;
    u.acceptedJob(this);
  }

  public void declineAppliance(final User u) {

    removeAppliance(u);
    u.declinedJob(this);
  }

  public JobOffer() {}

  public static Boolean areEqual(final JobOffer j1, final JobOffer j2) {

    Boolean andResult_9 = false;

    if (Utils.equals(j1.company, j2.company)) {
      Boolean andResult_10 = false;

      if (Utils.equals(j1.position, j2.position)) {
        Boolean andResult_11 = false;

        if (Utils.equals(j1.neededSkills, j2.neededSkills)) {
          Boolean andResult_12 = false;

          if (Utils.equals(j1.requiredEducation, j2.requiredEducation)) {
            if (Utils.equals(j1.requiredDriversLicense, j2.requiredDriversLicense)) {
              andResult_12 = true;
            }
          }

          if (andResult_12) {
            andResult_11 = true;
          }
        }

        if (andResult_11) {
          andResult_10 = true;
        }
      }

      if (andResult_10) {
        andResult_9 = true;
      }
    }

    return andResult_9;
  }

  public String toString() {

    return "JobOffer{"
        + "openPositions := "
        + Utils.toString(openPositions)
        + ", appliances := "
        + Utils.toString(appliances)
        + ", neededSkills := "
        + Utils.toString(neededSkills)
        + ", requiredEducation := "
        + Utils.toString(requiredEducation)
        + ", requiredDriversLicense := "
        + Utils.toString(requiredDriversLicense)
        + "}";
  }
}
