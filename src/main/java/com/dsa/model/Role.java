package com.dsa.model;

public enum Role {
    ADMIN,
// edit main tables
// // regions
// // courts

    JUDGE,
// see new Sue
// // initiate Lawsuit
// see own Lawsuits
// // make Verdict (accept / decline)

    ATTORNEY,
// initiate new Sue
// see own Lawsuits and its Verdicts
// see all public Verdicts

    GUEST
// see any public Verdicts

}
