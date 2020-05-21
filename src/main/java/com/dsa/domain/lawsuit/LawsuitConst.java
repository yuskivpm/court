package com.dsa.domain.lawsuit;

import com.dsa.domain.IEntity;

import org.jetbrains.annotations.Contract;

public class LawsuitConst {

  public static final String ID =IEntity.ID;
  public static final String ENTITY_NAME = "Lawsuit";
  public static final String SUE_DATE = "sueDate";
  public static final String COURT_ID = "courtId";
  public static final String SUITOR_ID = "suitorId";
  public static final String CLAIM_TEXT = "claimText";
  public static final String DEFENDANT_ID = "defendantId";
  public static final String DEFENDANT_TEXT = "defendantText";
  public static final String JUDGE_ID = "judgeId";
  public static final String START_DATE = "startDate";
  public static final String VERDICT_DATE = "verdictDate";
  public static final String VERDICT_TEXT = "verdictText";
  public static final String APPEALED_LAWSUIT_ID = "appealedLawsuitId";
  public static final String APPEALED_STATUS = "appealStatus";
  public static final String EXECUTION_DATE = "executionDate";

  private static final String PATH = "/lawsuits/";

  @Contract(pure = true)
  public static String getPath() {
    return PATH;
  }

}
