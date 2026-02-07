public record ActionResult(boolean applied, boolean stopEvaluation, String message) {
    public static ActionResult ok(String msg) {
        return new ActionResult(true, false, msg);
    }

    //if the target hex is already occupied by another minion or falls outside the boundary of the territory, the command acts like a no-op.
    // Whenever this command is executed (regardless of validity), the player's budget is decreased by one unit.
    public static ActionResult noOp(String msg) {
        return new ActionResult(false, false, msg);
    }

    //If the player does not have enough budget to execute this command,
    // the evaluation of the minion strategy in that turn ends.
    public static ActionResult terminate(String msg) {
        return new ActionResult(false, true, msg);
    }

    public static ActionResult terminateApplied(String msg) {
        return new ActionResult(true, true, msg);
    }
}