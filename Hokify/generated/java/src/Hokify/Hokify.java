package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Hokify {
  private VDMSet users = SetUtil.set();
  private VDMSet jobOffers = SetUtil.set();

  public VDMSet getUsers() {

    return Utils.copy(users);
  }

  public VDMSet getJobOffers() {

    return Utils.copy(jobOffers);
  }

  public VDMSet getMatchingOffersForUser(final User u) {

    return u.getMatchingOffers(Utils.copy(jobOffers));
  }

  public VDMSet getMatchingUsersForOffer(final JobOffer job) {

    return job.getMatchingOffers(Utils.copy(users));
  }

  public void addUser(final User u) {

    users = SetUtil.union(Utils.copy(users), SetUtil.set(u));
  }

  public void removeUser(final User u) {

    for (Iterator iterator_14 = users.iterator(); iterator_14.hasNext(); ) {
      User user = (User) iterator_14.next();
      if (User.areEqual(user, u)) {
        users = SetUtil.diff(Utils.copy(users), SetUtil.set(user));
      }
    }
  }

  public void addJobOffer(final JobOffer j) {

    jobOffers = SetUtil.union(Utils.copy(jobOffers), SetUtil.set(j));
  }

  public void removeJobOffer(final JobOffer j) {

    for (Iterator iterator_15 = jobOffers.iterator(); iterator_15.hasNext(); ) {
      JobOffer job = (JobOffer) iterator_15.next();
      if (JobOffer.areEqual(job, j)) {
        jobOffers = SetUtil.diff(Utils.copy(jobOffers), SetUtil.set(job));
      }
    }
  }

  public void addAppliance(final User u, final JobOffer j) {

    u.applyForJob(j);
  }

  public void acceptAppliance(final User u, final JobOffer j) {

    j.acceptAppliance(u);
  }

  public void declineAppliance(final User u, final JobOffer j) {

    j.declineAppliance(u);
  }

  public Hokify() {}

  public String toString() {

    return "Hokify{"
        + "users := "
        + Utils.toString(users)
        + ", jobOffers := "
        + Utils.toString(jobOffers)
        + "}";
  }
}
